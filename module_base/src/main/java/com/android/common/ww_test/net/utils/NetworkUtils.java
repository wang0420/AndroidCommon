package com.android.common.ww_test.net.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class NetworkUtils {

    private NetworkUtils() {
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前网速
     * 注意：一定要放在子线程中去获取
     * @param context
     * @return
     */
    public static String getCurNetSpeed(Context context) {
        if (context == null) return "";
        NetSpeed netSpeed = new NetSpeed();
        netSpeed.getNetSpeed(context.getApplicationInfo().uid);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return netSpeed.getNetSpeed(context.getApplicationInfo().uid);
    }
}
