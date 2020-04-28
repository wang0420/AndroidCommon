package com.app;


import android.util.Log;

import com.basemodule.BaseApplication;
import com.module.kt.AppModuleKt;

import org.koin.core.logger.Level;

import static org.koin.android.ext.koin.KoinExtKt.androidLogger;
import static org.koin.android.ext.koin.ModuleExtKt.androidContext;
import static org.koin.core.context.GlobalContextKt.startKoin;

/**
 * author chmyy
 * created on 2017/12/18
 * email fat_chao@163.com.
 */

public class MainApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("TAG", "---MainApplication");
        Log.w("TAG", "-----" + BuildConfig.DEBUG);
        // Start Koin

   /*     startKoin {
            androidLogger(Level.INFO)
            androidContext(this@App)
            modules(appModule)
        }*/

    }
}
