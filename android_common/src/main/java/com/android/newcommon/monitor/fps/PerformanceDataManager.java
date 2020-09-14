package com.android.newcommon.monitor.fps;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.view.Choreographer;

import com.android.newcommon.monitor.LogHelper;
import com.android.newcommon.monitor.entity.ReportEntity;
import com.android.newcommon.monitor.util.CpuUtils;
import com.android.newcommon.monitor.util.JsonUtil;

import androidx.annotation.RequiresApi;

/**
 * Created by wanglikun on 2018/9/13.
 */

public class PerformanceDataManager {
    private static final String TAG = "PerformanceDataManager";
    private static final int MAX_FRAME_RATE = 60;
    private static final int REFRESH_TIME = 3000;// 刷新频率
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private ActivityManager mActivityManager;
    private double mLastCpuRate;//CPU使用
    private String cpuAndMemory = "";//获取cpu和内存信息.

    private double mLastMemoryInfo;//内存使用
    private double mMaxMemory; //最大总内存
    private double mFPS = MAX_FRAME_RATE;//FPS  帧率
    private RecordAppFrameCallback mRecordFrameCallback = new RecordAppFrameCallback();
    private static final int MSG_CPU = 1;
    private static final int MSG_MEMORY = 2;
    private Application application;
    private boolean isMonitoring; //是否正在监听

    private ReportEntity reportEntity = new ReportEntity();


    @SuppressLint("StaticFieldLeak")
    private static PerformanceDataManager sInstance;

    public static PerformanceDataManager getInstance() {
        if (sInstance == null) {
            synchronized (PerformanceDataManager.class) {
                if (sInstance == null) {
                    sInstance = new PerformanceDataManager();
                }

            }
        }
        return sInstance;
    }


    public void init(Application app) {
        this.application = app;
        AppActiveMatrixDelegate.INSTANCE.init(application);
        mActivityManager = (ActivityManager) application.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        executeCpuData();
        executeMemoryData();
        if (mHandlerThread == null) {
            mHandlerThread = new HandlerThread("handler-thread");
            mHandlerThread.start();
        }

        if (mHandler == null) {
            mHandler = new Handler(mHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == MSG_CPU) {
                        executeCpuData();
                        mHandler.sendEmptyMessageDelayed(MSG_CPU, REFRESH_TIME);
                    }
                    if (msg.what == MSG_MEMORY) {
                        executeMemoryData();
                        mHandler.sendEmptyMessageDelayed(MSG_MEMORY, REFRESH_TIME);
                    }
                }
            };
        }
    }


    /*-----------------------------------性能检测API---------------------------------------------*/
    public double getMaxMemory() {
        return mMaxMemory;
    }


    public double getLastFrameRate() {
        return mFPS;
    }

    public double getLastCpuRate() {
        return mLastCpuRate;
    }

    public double getLastMemoryInfo() {
        return mLastMemoryInfo;
    }

    public String getCpuAndMemory() {
        return cpuAndMemory;
    }

    /**
     * 开始性能检测
     */
    public void startUploadMonitorData() {
        isMonitoring = true;
        startMonitorFrameInfo();
        startMonitorCPUInfo();
        startMonitorMemoryInfo();

    }

    /**
     * 停止性能检测
     */
    public void stopUploadMonitorData() {
        isMonitoring = false;
        stopMonitorFrameInfo();
        stopMonitorCPUInfo();
        stopMonitorMemoryInfo();
        if (mHandlerThread != null) {
            //结束线程，即停止线程的消息循环
            mHandlerThread.quit();
        }
        mHandlerThread = null;
        mHandler = null;

    }


    /**
     * 延迟获取CPU
     */
    private void startMonitorCPUInfo() {
        mHandler.sendEmptyMessageDelayed(MSG_CPU, REFRESH_TIME);
    }

    /**
     * 获取FPS
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startMonitorFrameInfo() {
        Choreographer.getInstance().postFrameCallback(mRecordFrameCallback);

    }

    /**
     * 开始内存检测
     */
    private void startMonitorMemoryInfo() {
        if (mMaxMemory == 0) {
            mMaxMemory = mActivityManager.getMemoryClass();
        }
        mHandler.sendEmptyMessageDelayed(MSG_MEMORY, REFRESH_TIME);
    }


    /**
     * 停止获取FPS
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void stopMonitorFrameInfo() {
        Choreographer.getInstance().removeFrameCallback(mRecordFrameCallback);
        mMainHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 停止获取内存
     */
    private void stopMonitorMemoryInfo() {
        mHandler.removeMessages(MSG_MEMORY);
    }

    /**
     * 停止获取CPU
     */
    private void stopMonitorCPUInfo() {
        mHandler.removeMessages(MSG_CPU);
    }


    /*-----------------------------------memory---------------------------------------------*/
    private void executeMemoryData() {
        mLastMemoryInfo = getMemoryData();
        LogHelper.d(TAG, "memory info is =" + mLastMemoryInfo);

        ReportEntity.MemoryBean memoryBean = new ReportEntity.MemoryBean();
        memoryBean.setTotal(mMaxMemory);
        memoryBean.setUserMemory(mLastMemoryInfo);
        memoryBean.setFree(mMaxMemory - mLastMemoryInfo);
        reportEntity.setMemory(memoryBean);

    }

    private double getMemoryData() {
        double mem = 0.0F;
        try {
            // 统计进程的内存信息 totalPss
            final Debug.MemoryInfo[] memInfo = mActivityManager.getProcessMemoryInfo(new int[]{Process.myPid()});
            if (memInfo.length > 0) {
                // TotalPss = dalvikPss + nativePss + otherPss, in KB
                final int totalPss = memInfo[0].getTotalPss();
                if (totalPss >= 0) {
                    // Mem in MB
                    mem = totalPss / 1024.0F;
                }
            }
        } catch (Exception e) {
            LogHelper.e(TAG, "getMemoryData fail: " + e.toString());
        }
        return mem;
    }

    /*-----------------------------------CPU---------------------------------------------*/

    private void executeCpuData() {
        //android  o  version   是否是8.0及其以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLastCpuRate = CpuUtils.getCpuDataForO();
        } else {
            mLastCpuRate = CpuUtils.getCPUData();
        }

        ReportEntity.CpuBean cpuBean = new ReportEntity.CpuBean();
        cpuBean.setUsage(mLastCpuRate + "%");
        cpuBean.setCount(CpuUtils.getCpuCoreCount());
        cpuBean.setName(CpuUtils.getCpuName());
        cpuBean.setArchitecture(CpuUtils.getCpuArchitecture());
        reportEntity.setCpu(cpuBean);
    }

    /*-----------------------------------FPS---------------------------------------------*/

    /**
     * 记录Fps
     */

    private class RecordAppFrameCallback implements Choreographer.FrameCallback {
        private long mLastFrameTime = 0; // 记录上一帧开始绘制的时间
        private int mFrameCount = 0; // 间隔时间的帧率 （在一段时间里面绘制了多少帧）

        @Override
        public void doFrame(long frameTimeNanos) {
            //此地方单位是纳秒 需要转换成毫秒
            if (mLastFrameTime == 0) {
                mLastFrameTime = frameTimeNanos;
            }
            //纳秒转换得到毫秒，正常是 16.66 ms
            double diff = (frameTimeNanos - mLastFrameTime) / 1000000.0f;
            //记录 REFRESH_TIME ms绘制的帧率 得到平均FPS(1s内理论上应该绘制约60帧)
            if (diff >= REFRESH_TIME) {
                mFPS = (mFrameCount * 1000F) / diff;
                LogHelper.w(TAG, AppActiveMatrixDelegate.INSTANCE.getVisibleScene() + "-mFrameCount--" + mFrameCount + "-diff--" + diff + "---mFPS---" + mFPS);
                mFrameCount = 0;
                mLastFrameTime = 0;
            } else {
                ++mFrameCount;
            }
           // if (mFPS < 55) {
                // 如果小于30  一般就标识有丢帧
                // log();
                ReportEntity.FpsBean fpsBean = new ReportEntity.FpsBean();
                fpsBean.setFps(mFPS);
                fpsBean.setScene(AppActiveMatrixDelegate.INSTANCE.getVisibleScene().hashCode());
                fpsBean.setDesc(AppActiveMatrixDelegate.INSTANCE.getVisibleScene());
                reportEntity.setFps(fpsBean);
                LogHelper.e(TAG, "fps report info =" + JsonUtil.jsonFromObject(reportEntity));
           // }

            if (mRecordFrameCallback != null) {
                Choreographer.getInstance().postFrameCallback(mRecordFrameCallback);
            }
        }
    }

    /**
     * 输出当前异常或及错误堆栈信息。
     */
    private void log() {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
        for (StackTraceElement s : stackTrace) {
            sb.append(s + "\n");
        }
        LogHelper.w(TAG, "stack log" + sb.toString());
    }

}
