package com.android.newcommon.monitor.util;

import android.app.Application;
import android.content.Intent;

import com.android.newcommon.monitor.crash.CrashService1;

/**
 * @author wangwei
 * @date 2020/8/28.
 */
public class Utils {
    public static String FLAG_INFO = "info";

    public static void startCrashService(Application application, String info) {
        Intent intent = new Intent(application, CrashService1.class);
        intent.putExtra(FLAG_INFO, info);
        application.startService(intent);
    }
}
