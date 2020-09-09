package com.app;


import android.util.Log;

import com.android.common.BaseApplication;
import com.app.matrix.TestPluginListener;
import com.app.matrix.config.DynamicConfigImplDemo;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;

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


        initMatrix();

    }

    private void initMatrix() {
        Log.e("matrix--", "MatrixApplication.onCreate");
        DynamicConfigImplDemo dynamicConfig = new DynamicConfigImplDemo();
        boolean matrixEnable = dynamicConfig.isMatrixEnable();
        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();
        Matrix.Builder builder = new Matrix.Builder(this);
        builder.patchListener(new TestPluginListener(this));
        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(true)
                .enableEvilMethodTrace(true)
                .enableAnrTrace(true)
                .enableStartup(true)
                .splashActivities("com.app.SplashActivity;")
                .isDebug(true)
                .isDevEnv(true)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);
        Matrix.init(builder.build());
        //start only startup tracer, close other tracer.
        tracePlugin.start();
    }
}
