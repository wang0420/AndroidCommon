package com.app;


import android.util.Log;

import com.basemodule.ModuleApplication;

/**
 * author chmyy
 * created on 2017/12/18
 * email fat_chao@163.com.
 */

public class MainApplication extends ModuleApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("TAG", "---BApplication");
    }
}
