package com.android.common;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.newcommon.floatview.FloatPageManager;
import com.android.newcommon.monitor.block.BlockMonitorManager;
import com.android.newcommon.monitor.crash.CrashCaptureManager;
import com.android.newcommon.monitor.fps.PerformanceDataManager;

import androidx.multidex.MultiDex;


public class BaseApplication extends Application {

    private static BaseApplication sInstance;


    public static BaseApplication getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Application has not been created");
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //BlockCanary.install(this, new MyBlockCanaryContext()).start();//在主进程初始化调用
        //Choreographer.getInstance().postFrameCallback(new FPSFrameCallback(System.nanoTime()));
        if (sInstance == null) {
            sInstance = this;
        }

        // if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        // }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        /*---------------性能相关---------------------*/

        //初始化悬浮
        FloatPageManager.getInstance().init(this);
        //初始化卡顿
        BlockMonitorManager.getInstance().start(this);
        //Crash日志
        CrashCaptureManager.getInstance().init(this);
        //FPS
        PerformanceDataManager.getInstance().init(this);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 65535
        MultiDex.install(this);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}