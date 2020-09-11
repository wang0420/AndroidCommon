package com.android.newcommon.monitor.util;

import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import com.android.newcommon.monitor.LogHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;

/**
 * @author wangwei
 * @date 2020/9/10.
 */
public class CpuUtils {
    private static final String TAG = "CpuUtils";

    /**
     * 注意：cpu和memory静态字段是MATRIX_VERSION=0.5.2版本源码里面的，需要一样才能解析，使用jsonObject解析效率高
     */
    private static final String DEVICE_MEMORY_FREE = "mem_free";
    private static final String DEVICE_MEMORY = "mem";
    private static final String DEVICE_CPU = "cpu_app";

    private static RandomAccessFile mProcStatFile;
    private static RandomAccessFile mAppStatFile;
    private static Long mLastCpuTime;
    private static Long mLastAppCpuTime;


    /**
     * 获取cpu和内存信息
     */
    public static String getCpuAndMemory() {
        return "\nCPU核心个数:" + getCpuCoreCount() + "\nCPU名字:" + getCpuName() + "\ncpu类型和架构:" + getCpuArchitecture();

    }

    /**
     * 获取CPU核心个数.
     */
    private static int getCpuCoreCount() {
        String CPU_FILE_PATH = "/sys/devices/system/cpu/";

        int coreCount = 1;
        try {
            File dir = new File(CPU_FILE_PATH);
            String[] cpuFiles = dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return Pattern.matches("cpu\\d{1}", name);
                }
            });
            if (cpuFiles != null && cpuFiles.length > 0) {
                coreCount = cpuFiles.length;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coreCount;
    }

    /**
     * 获取CPU名字
     */
    private static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            if (array.length > 1) {
                return array[1];
            }
            return "null";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }

    /**
     * 获取cpu类型和架构
     */
    public static String getCpuArchitecture() {
        String[] abis = new String[]{};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        StringBuilder abiStr = new StringBuilder();
        for (String abi : abis) {
            abiStr.append(abi);
            abiStr.append(',');
        }
        return abiStr.toString();
    }


    /**
     * android  o 获取CPU 信息
     */
    public static double getCpuDataForO() {
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

    private static int getCPUIndex(String line) {
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


    public static double getCPUData() {
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

}
