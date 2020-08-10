package com.android.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.newcommon.utils.anr.FPSFrameCallback;
import com.android.newcommon.utils.anr.MyBlockCanaryContext;
import com.github.moduth.blockcanary.BlockCanary;

import androidx.multidex.MultiDex;


public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

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
        BlockCanary.install(this, new MyBlockCanaryContext()).start();//在主进程初始化调用
        Choreographer.getInstance().postFrameCallback(new FPSFrameCallback(System.nanoTime()));
        if (sInstance == null) {
            sInstance = this;
        }
      //  Debug.startMethodTracing(FileUtils.getInstance().getAnrFile().getAbsolutePath() + File.separator + "test.trace");
        registerActivityLifecycleCallbacks(this);
        // if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        // }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

       // Debug.stopMethodTracing();


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 65535
        MultiDex.install(this);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.w("TAG", activity.getClass().getSimpleName() + "-----onActivityCreated");

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.w("TAG", activity.getClass().getSimpleName() + "-----onActivityResumed");

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.w("TAG", activity.getClass().getSimpleName() + "-----onActivityDestroyed");

    }
}