package com.android.newcommon.utils;

import android.util.Log;

/**
 * Created by wangwei on 2020/5/9.
 * Android系统对日志长度有限制的，最大长度为4K（注意是字符串的长度），超过这个范围的自动截断，所以就会出现打印不全的情况。
 * 从Android内核源码来看，在logger.h头文件中有以下宏定义：
 */

public class LogUtil {

    private static int LOG_MAX_LENGTH = 4000;
    private static String TAG = "TAG";

    public static void d(String msg) {
        w(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    private static void w(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAX_LENGTH;
        while (end < strLength) {
            Log.w(TAG, msg.substring(start, end));
            start = end;
            end = end + LOG_MAX_LENGTH;
        }
        Log.w(TAG, msg.substring(start, strLength));
    }


}
