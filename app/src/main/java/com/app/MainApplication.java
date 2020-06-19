package com.app;


import android.util.Log;

import com.android.common.BaseApplication;

import org.koin.android.java.KoinAndroidApplication;
import org.koin.core.KoinApplication;
import org.koin.core.context.GlobalContext;
import org.koin.core.logger.Level;

import static com.module.kt.AppModuleKt.appModule;
import static com.module.kt.GirlModuleKt.girlModule;
import static org.koin.core.context.ContextFunctionsKt.startKoin;

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

        KoinApplication koinApplication = KoinAndroidApplication.create(this, Level.INFO)
                .modules(appModule, girlModule);
        startKoin(new GlobalContext(), koinApplication);



   /*     startKoin {
            androidLogger(Level.INFO)
            androidContext(this@App)
            modules(appModule)
        }*/

    }
}
