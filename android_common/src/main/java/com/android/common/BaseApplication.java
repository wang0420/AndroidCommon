package com.android.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.newcommon.floatview.FloatPageManager;
import com.android.newcommon.monitor.LiveMonitorUtils;
import com.android.newcommon.monitor.block.core.BlockMonitorManager;
import com.android.newcommon.monitor.crash.CrashCaptureManager;
import com.yhao.floatwindow.PermissionListener;
import com.yhao.floatwindow.ViewStateListener;

import androidx.multidex.MultiDex;

import static android.widget.LinearLayout.VERTICAL;


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

        //BlockCanary.install(this, new MyBlockCanaryContext()).start();//在主进程初始化调用
        //Choreographer.getInstance().postFrameCallback(new FPSFrameCallback(System.nanoTime()));
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

        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(VERTICAL);
        //imageView.setImageResource(R.drawable.ic_launcher);
        LiveMonitorUtils.startLiveMonitor(this, linearLayout);
/*        FloatWindow
                .with(getApplicationContext())
                .setView(linearLayout)
                .setWidth(Screen.width, 0.4f) //设置悬浮控件宽高
                .setHeight(Screen.width, 0.4f)
                .setMoveType(MoveType.slide, 100, -100)
                .setMoveStyle(500, new BounceInterpolator())
                .setViewStateListener(mViewStateListener)
                .setPermissionListener(mPermissionListener)
                .setDesktopShow(false)
                .build();*/
        FloatPageManager.getInstance().init(this);
        this.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            int startedActivityCounts;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (startedActivityCounts == 0) {
                    FloatPageManager.getInstance().notifyForeground();
                }
                startedActivityCounts++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                startedActivityCounts--;
                if (startedActivityCounts == 0) {
                    FloatPageManager.getInstance().notifyBackground();
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });


        LiveMonitorUtils.startLiveMonitor(this, linearLayout);// 显示FPS  内存
        //初始化卡顿
        BlockMonitorManager.getInstance().start(this);
        //Crash日志
        CrashCaptureManager.getInstance().init(this, true);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseApplication.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String TAG = "FloatWindow";

    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };

    private ViewStateListener mViewStateListener = new ViewStateListener() {
        @Override
        public void onPositionUpdate(int x, int y) {
            Log.d(TAG, "onPositionUpdate: x=" + x + " y=" + y);
        }

        @Override
        public void onShow() {
            Log.d(TAG, "onShow");
        }

        @Override
        public void onHide() {
            Log.d(TAG, "onHide");
        }

        @Override
        public void onDismiss() {
            Log.d(TAG, "onDismiss");
        }

        @Override
        public void onMoveAnimStart() {
            Log.d(TAG, "onMoveAnimStart");
        }

        @Override
        public void onMoveAnimEnd() {
            Log.d(TAG, "onMoveAnimEnd");
        }

        @Override
        public void onBackToDesktop() {
            Log.d(TAG, "onBackToDesktop");
        }
    };

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