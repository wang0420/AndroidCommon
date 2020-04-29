package com.moudlea.jetpackStudy.lifecycles;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by wangwei on 2020/4/29.
 * 添加观察者
 * LocationListener可以感知到Activity生命周期
 */

public class LocationListener implements LifecycleObserver {
    private static final String TAG = "TAG";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onActivityCreate(LifecycleOwner owner) {
        Log.w(TAG, "onActivityCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onActivityDestroy(LifecycleOwner owner) {
        Log.w(TAG, "onActivityDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onActivityPause(LifecycleOwner owner) {
        Log.w(TAG, "onActivityPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onActivityResume(LifecycleOwner owner) {
        Log.w(TAG, "onActivityResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onActivityStart(LifecycleOwner owner) {
        Log.w(TAG, "onActivityStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onActivityStop(LifecycleOwner owner) {
        Log.w(TAG, "onActivityStop");
    }

}
