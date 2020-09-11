package com.android.newcommon.monitor.fps.discard;

import android.content.Context;
import android.util.Log;
import android.view.Choreographer;
import android.widget.Toast;

import com.android.common.BaseApplication;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * App监控
 *
 * @author huliang
 * @date 2019/5/9
 */

public class AppMonitor {
    private static final String TAG = AppMonitor.class.getSimpleName();
    private EffectFrameCallback mFrameCallback;
    private RecordFrameCallback mRecordFrameCallback;
    private MonitorCallback mMonitorCallback;
    private CheckMonitorCallback mCheckMonitorCallback;
    private boolean isLog = true;
    private Context mContext;

    /**
     * 开始检测Fps
     */
    public void startMonitorFps(CheckMonitorCallback callBack) {
        if (mFrameCallback != null) {
            return;
        }
        mCheckMonitorCallback = callBack;
        mFrameCallback = new EffectFrameCallback();
        Choreographer.getInstance().postFrameCallback(mFrameCallback);
    }

    private int interval = 500; // 记录间隔时间


    public void startLogMonitorFps(Context context, MonitorCallback callBack) {
        mContext = context;
        if (mRecordFrameCallback != null) {
            return;
        }
        isLog = true;
        mMonitorCallback = callBack;
        mRecordFrameCallback = new RecordFrameCallback();
        Choreographer.getInstance().postFrameCallback(mRecordFrameCallback);
        getProcessCpuRate();
    }

    /**
     * 停止检测Fps
     */
    public void stopMonitorFps() {
        mMonitorCallback = null;
        mFrameCallback = null;
        mRecordFrameCallback = null;
        isLog = false;
    }

    /**
     * 帧率性能判断
     */
    private class EffectFrameCallback implements Choreographer.FrameCallback {
        private String TAG = EffectFrameCallback.class.getSimpleName();
        private long totalTimes = 0;
        private double totalFps = 0;

        private int maxCount = 10; // 最大检测次数
        private int minFps = 20; // 最小帧率

        private long frameStartTime = 0; // 开始时间
        private int framesRendered = 0; // 间隔时间的帧率


        @Override
        public void doFrame(long frameTimeNanos) {
            long currentTimeMillis = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos);
            if (frameStartTime > 0) {
                // take the span in milliseconds
                final long timeSpan = currentTimeMillis - frameStartTime;
                framesRendered++;

                if (timeSpan > interval) {
                    totalTimes += timeSpan;
                    final double fps = framesRendered * 1000 / (double) timeSpan;
                    totalFps += fps;
                    Log.e("EffectFrameCallback", "doFrame: fps=" + fps);
                    frameStartTime = currentTimeMillis;
                    framesRendered = 0;
                }
            } else {
                frameStartTime = currentTimeMillis;
            }

            if (totalTimes >= interval * maxCount) {
                double averageFps = totalFps / maxCount;
                Log.e("EffectFrameCallback", "doFrame: averageFps=" + averageFps);

                //为了方便测试，打印toast
                Toast.makeText(BaseApplication.getInstance(), "平均帧率为：" + averageFps, Toast.LENGTH_LONG).show();

                if (averageFps < minFps) {
                    // 手机性能太差
                    if (mCheckMonitorCallback != null) {
                        mCheckMonitorCallback.fpsTooLow();
                    }
                }
                mFrameCallback = null;
            }

            if (mFrameCallback != null) {
                Choreographer.getInstance().postFrameCallback(mFrameCallback);
            }
        }
    }

    /**
     * 记录Fps
     */
    private class RecordFrameCallback implements Choreographer.FrameCallback {
        private String TAG = RecordFrameCallback.class.getSimpleName();
        private long mLastFrameTime = 0; // 记录上一帧开始绘制的时间
        private int mFrameCount = 0; // 间隔时间的帧率 （在一段时间里面绘制了多少帧）

        @Override
        public void doFrame(long frameTimeNanos) {
            //此地方单位是纳秒 需要转换成毫秒
            if (mLastFrameTime == 0) {
                mLastFrameTime = frameTimeNanos;
            }
            //纳秒转换得到毫秒，正常是 16.66 ms
            float diff = (frameTimeNanos - mLastFrameTime) / 1000000.0f;
            //记录1000ms绘制的帧率 得到平均FPS(1s内理论上应该绘制约60帧)
            if (diff > 1000) {
                double fps = (((double) (mFrameCount * 1000L)) / diff);
                //  Log.e("wangwei", "doFrame: " + fps + "mFrameCount--" + mFrameCount);
                mFrameCount = 0;
                mLastFrameTime = 0;
                if (mMonitorCallback != null) {
                    mMonitorCallback.framePerSecond(fps);
                }
                getAppMemory();

            } else {
                ++mFrameCount;
            }

            if (mRecordFrameCallback != null) {
                Choreographer.getInstance().postFrameCallback(mRecordFrameCallback);
            }
        }
    }

    private void getProcessCpuRate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isLog) {
                        float totalCpuTime1 = getTotalCpuTime();
                        float processCpuTime1 = getAppCpuTime();

                        Thread.sleep(interval);  //sleep一段时间

                        float totalCpuTime2 = getTotalCpuTime();
                        float processCpuTime2 = getAppCpuTime();
                        float cpuRate = 100 * (processCpuTime2 - processCpuTime1) / (totalCpuTime2 - totalCpuTime1);//百分比
                        Log.e("wangwei", "doFrame: getProcessCpuRate=" + cpuRate);

                        if (mMonitorCallback != null) {
                            mMonitorCallback.cpuRate(cpuRate);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    // 获取系统总CPU使用时间
    public long getTotalCpuTime() throws Exception {
        String[] cpuInfos = null;
        long totalCpu = 1;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");

            totalCpu = Long.parseLong(cpuInfos[2])
                    + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                    + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                    + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        } catch (Exception ex) {
            throw ex;
        }

        return totalCpu;
    }

    // 获取应用占用的CPU时间
    public long getAppCpuTime() {
        String[] cpuInfos = null;
        long appCpuTime = 1;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
            appCpuTime = Long.parseLong(cpuInfos[13])
                    + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                    + Long.parseLong(cpuInfos[16]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return appCpuTime;
    }

    /**
     * 获取应用占用的内存(单位为KB)
     */
    private void getAppMemory() {
        String info = null;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/status")), 1000);
            String load;
            while ((load = reader.readLine()) != null) {
                load = load.replace(" ", "");
                String[] Info = load.split("[: k K]");
                if (Info[0].equals("VmRSS")) {
                    info = Info[1];
                    break;
                }

            }
            reader.close();
            if (mMonitorCallback != null) {
                mMonitorCallback.appMemory(StringFormatUtils.string2Long(info != null ? info.trim() : "0"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
