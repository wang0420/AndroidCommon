package com.android.newcommon.monitor;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.view.Choreographer;

import com.android.newcommon.monitor.block.core.LogHelper;
import com.android.newcommon.monitor.util.threadpool.ThreadPoolProxyFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import androidx.annotation.RequiresApi;

/**
 * Created by wanglikun on 2018/9/13.
 */

public class PerformanceDataManager {
    private static final String TAG = "PerformanceDataManager";
    private static final int MAX_FRAME_RATE = 60;
    private static final int REFRESH_TIME = 1000;// 刷新频率

    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());


    private Context mContext;
    private ActivityManager mActivityManager;

    private RandomAccessFile mProcStatFile;
    private RandomAccessFile mAppStatFile;
    private Long mLastCpuTime;
    private Long mLastAppCpuTime;
    private double mLastCpuRate;//CPU使用
    private double mLastMemoryInfo;//内存使用
    private double mMaxMemory; //最大内存
    private double mFPS = MAX_FRAME_RATE;//FPS  帧率
    private RecordAppFrameCallback mRecordFrameCallback = new RecordAppFrameCallback();

    private static final int MSG_CPU = 1;
    private static final int MSG_MEMORY = 2;


    private boolean isMonitoring; //是否正在监听

    public boolean isMonitoring() {
        return isMonitoring;
    }

    private static class Holder {
        private static PerformanceDataManager INSTANCE = new PerformanceDataManager();
    }

    private PerformanceDataManager() {
    }

    public static PerformanceDataManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
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
                        mHandler.sendEmptyMessageDelayed(MSG_CPU, 1000);
                    }
                    if (msg.what == MSG_MEMORY) {
                        executeMemoryData();
                        mHandler.sendEmptyMessageDelayed(MSG_MEMORY, 1000);
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
        mHandler.sendEmptyMessageDelayed(MSG_CPU, 1000);
    }

    /**
     * 获取FPS
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startMonitorFrameInfo() {
        Choreographer.getInstance().postFrameCallback(mRecordFrameCallback);

    }

    /**
     * 停止内存检测
     */
    private void startMonitorMemoryInfo() {
        if (mMaxMemory == 0) {
            mMaxMemory = mActivityManager.getMemoryClass();
        }
        mHandler.sendEmptyMessageDelayed(MSG_MEMORY, 1000);
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
            mLastCpuRate = getCpuDataForO();
            LogHelper.d(TAG, "cpu info is =" + mLastCpuRate);
        } else {
            mLastCpuRate = getCPUData();
            LogHelper.d(TAG, "cpu info is =" + mLastCpuRate);
        }
    }

    private double getCpuDataForO() {
        java.lang.Process process = null;
        try {
            process = Runtime.getRuntime().exec("top -n 1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int cpuIndex = -1;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (TextUtils.isEmpty(line)) {
                    continue;
                }
                int tempIndex = getCPUIndex(line);
                if (tempIndex != -1) {
                    cpuIndex = tempIndex;
                    continue;
                }
                if (line.startsWith(String.valueOf(Process.myPid()))) {
                    if (cpuIndex == -1) {
                        continue;
                    }
                    String[] param = line.split("\\s+");
                    if (param.length <= cpuIndex) {
                        continue;
                    }
                    String cpu = param[cpuIndex];
                    if (cpu.endsWith("%")) {
                        cpu = cpu.substring(0, cpu.lastIndexOf("%"));
                    }
                    double rate = Double.parseDouble(cpu) / Runtime.getRuntime().availableProcessors();
                    return rate;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return 0;
    }

    private int getCPUIndex(String line) {
        if (line.contains("CPU")) {
            String[] titles = line.split("\\s+");
            for (int i = 0; i < titles.length; i++) {
                if (titles[i].contains("CPU")) {
                    return i;
                }
            }
        }
        return -1;
    }

    private double getCPUData() {
        long cpuTime;
        long appTime;
        double value = 0.0f;
        try {
            if (mProcStatFile == null || mAppStatFile == null) {
                mProcStatFile = new RandomAccessFile("/proc/stat", "r");
                mAppStatFile = new RandomAccessFile("/proc/" + Process.myPid() + "/stat", "r");
            } else {
                mProcStatFile.seek(0L);
                mAppStatFile.seek(0L);
            }
            String procStatString = mProcStatFile.readLine();
            String appStatString = mAppStatFile.readLine();
            String procStats[] = procStatString.split(" ");
            String appStats[] = appStatString.split(" ");
            cpuTime = Long.parseLong(procStats[2]) + Long.parseLong(procStats[3])
                    + Long.parseLong(procStats[4]) + Long.parseLong(procStats[5])
                    + Long.parseLong(procStats[6]) + Long.parseLong(procStats[7])
                    + Long.parseLong(procStats[8]);
            appTime = Long.parseLong(appStats[13]) + Long.parseLong(appStats[14]);
            if (mLastCpuTime == null && mLastAppCpuTime == null) {
                mLastCpuTime = cpuTime;
                mLastAppCpuTime = appTime;
                return value;
            }
            value = ((double) (appTime - mLastAppCpuTime) / (double) (cpuTime - mLastCpuTime)) * 100f;
            mLastCpuTime = cpuTime;
            mLastAppCpuTime = appTime;
        } catch (Exception e) {
            LogHelper.e(TAG, "getCPUData fail: " + e.toString());
        }
        return value;
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
            //记录1000ms绘制的帧率 得到平均FPS(1s内理论上应该绘制约60帧)
            if (diff >= 1000) {
                mFPS = (mFrameCount * 1000F) / diff;
                mFrameCount = 0;
                mLastFrameTime = 0;
                LogHelper.d(TAG, "fps info is =" + mFPS);


            } else {
                ++mFrameCount;
            }

            if (mRecordFrameCallback != null) {
                Choreographer.getInstance().postFrameCallback(mRecordFrameCallback);
            }
        }
    }


}
