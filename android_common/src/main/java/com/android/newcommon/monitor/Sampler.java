package com.android.newcommon.monitor;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Process;
import android.util.Log;

import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wangwei
 * @date 2020/8/27.
 */
public class Sampler implements Runnable {

    private volatile static Sampler instance = null;
    private ScheduledExecutorService scheduler;
    private ActivityManager activityManager;
    private long freq;
    private Long lastCpuTime;
    private Long lastAppCpuTime;
    private RandomAccessFile procStatFile;
    private RandomAccessFile appStatFile;

    private Sampler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    public static Sampler getInstance() {
        if (instance == null) {
            synchronized (Sampler.class) {
                if (instance == null) {
                    instance = new Sampler();
                }
            }
        }
        return instance;
    }

    // freq为采样周期
    public void init(Context context, long freq) {
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        this.freq = freq;
    }

    public void start() {
        scheduler.scheduleWithFixedDelay(this, 0L, freq, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        double cpu = sampleCPU();
        double mem = sampleMemory();
        Log.d("wangwei", "CPU: " + cpu + "%" + "    Memory: " + mem + "MB");
    }

    private double sampleCPU() {
        long cpuTime;
        long appTime;
        double sampleValue = 0.0D;
        try {
            if (procStatFile == null || appStatFile == null) {
                procStatFile = new RandomAccessFile("/proc/stat", "r");
                appStatFile = new RandomAccessFile("/proc/" + Process.myPid() + "/stat", "r");
            } else {
                procStatFile.seek(0L);
                appStatFile.seek(0L);
            }
            String procStatString = procStatFile.readLine();
            String appStatString = appStatFile.readLine();
            String procStats[] = procStatString.split(" ");
            String appStats[] = appStatString.split(" ");
            cpuTime = Long.parseLong(procStats[2]) + Long.parseLong(procStats[3])
                    + Long.parseLong(procStats[4]) + Long.parseLong(procStats[5])
                    + Long.parseLong(procStats[6]) + Long.parseLong(procStats[7])
                    + Long.parseLong(procStats[8]);
            appTime = Long.parseLong(appStats[13]) + Long.parseLong(appStats[14]);
            if (lastCpuTime == null && lastAppCpuTime == null) {
                lastCpuTime = cpuTime;
                lastAppCpuTime = appTime;
                return sampleValue;
            }
            sampleValue = ((double) (appTime - lastAppCpuTime) / (double) (cpuTime - lastCpuTime)) * 100D;
            lastCpuTime = cpuTime;
            lastAppCpuTime = appTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sampleValue;
    }

    private double sampleMemory() {
        double mem = 0.0D;
        try {
            // 统计进程的内存信息 totalPss
            final Debug.MemoryInfo[] memInfo = activityManager.getProcessMemoryInfo(new int[]{Process.myPid()});
            if (memInfo.length > 0) {
                // TotalPss = dalvikPss + nativePss + otherPss, in KB
                final int totalPss = memInfo[0].getTotalPss();
                if (totalPss >= 0) {
                    // Mem in MB
                    mem = totalPss / 1024.0D;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mem;
    }
}
