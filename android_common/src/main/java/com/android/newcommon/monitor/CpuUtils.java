package com.android.newcommon.monitor;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author wangwei
 * @date 2020/9/10.
 */
public class CpuUtils {
    /**
     * 注意：cpu和memory静态字段是MATRIX_VERSION=0.5.2版本源码里面的，需要一样才能解析，使用jsonObject解析效率高
     */
    private static final String DEVICE_MEMORY_FREE = "mem_free";
    private static final String DEVICE_MEMORY = "mem";
    private static final String DEVICE_CPU = "cpu_app";

    private static final String CPU_FILE_PATH = "/sys/devices/system/cpu/";

    /**
     * 获取cpu和内存信息
     */
    public static String getCpuAndMemory() {
        return "\nCPU核心个数" + getCpuCoreCount() + "\nCPU名字" + getCpuName() + "\ncpu类型和架构" + getCpuArchitecture();

    }

    /**
     * 获取CPU核心个数.
     */
    private static int getCpuCoreCount() {
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

}
