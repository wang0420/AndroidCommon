package com.android.newcommon.monitor.fps.discard;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author wangwei
 * @date 2020/8/27.
 */
public class MemoryUtil {
    private static ActivityManager activityManager;

    @TargetApi(Build.VERSION_CODES.M)
    public static double getRunningMemory(Context context) {
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        double mem = 0.0D;
        try {
            // 统计进程的内存信息 totalPss
            final Debug.MemoryInfo[] memInfo = activityManager.getProcessMemoryInfo(new int[]{Process.myPid()});
            if (memInfo.length > 0) {

                /**
                 * 读取内存信息,跟Android Profiler 分析一致
                 */
                String java_mem = memInfo[0].getMemoryStat("summary.java-heap");

                String native_mem = memInfo[0].getMemoryStat("summary.native-heap");

                String graphics_mem = memInfo[0].getMemoryStat("summary.graphics");

                String stack_mem = memInfo[0].getMemoryStat("summary.stack");

                String code_mem = memInfo[0].getMemoryStat("summary.code");

                String others_mem = memInfo[0].getMemoryStat("summary.system");

                final int dalvikPss = convertToInt(java_mem,0)
                        + convertToInt(native_mem,0)
                        + convertToInt(graphics_mem,0)
                        + convertToInt(stack_mem,0)
                        + convertToInt(code_mem,0)
                        + convertToInt(others_mem,0);

                if (dalvikPss >= 0) {
                    // Mem in MB
                    mem = dalvikPss / 1024.0D;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mem;
    }

    /**
     * 转化为int
     * @param value 传入对象
     * @param defaultValue 发生异常时，返回默认值
     * @return
     */
    public final static int convertToInt(Object value, int defaultValue){

        if (value == null || "".equals(value.toString().trim())){
            return defaultValue;
        }

        try {
            return Integer.valueOf(value.toString());
        }catch (Exception e){

            try {
                return Integer.valueOf(String.valueOf(value));
            }catch (Exception e1) {

                try {
                    return Double.valueOf(value.toString()).intValue();
                }catch (Exception e2){
                    return defaultValue;
                }
            }
        }
    }












}
