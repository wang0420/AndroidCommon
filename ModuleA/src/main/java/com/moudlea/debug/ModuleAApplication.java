package com.moudlea.debug;

import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ModuleApplication;

/**
 * @author GaoXP
 * @time 2018/2/27.
 */

public class ModuleAApplication extends ModuleApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("TAG", "---ModuleAApplication");

    }
}
