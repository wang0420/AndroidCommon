package com.moudlea.debug;

import android.util.Log;

import com.android.common.BaseApplication;

/**
 * @author GaoXP
 * @time 2018/2/27.
 */

public class ModuleAApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("TAG", "---ModuleAApplication");

    }
}
