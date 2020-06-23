package com.android.newcommon.net.retrofit;

import com.android.newcommon.net.utils.Callback;

import io.reactivex.observers.DefaultObserver;


/**
 * 网络请求的观察者
 */
public class BaseSubscriber<T> extends DefaultObserver<T> {

    private Callback<T> callback;


    public BaseSubscriber(Callback<T> callback) {
        this.callback = callback;
    }

    public void onBegin() {
        if (callback != null) {
            callback.onBegin();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (callback != null) {
            callback.onError(e);
            callback.onEnd();
        }

    }

    @Override
    public void onComplete() {
        if (callback != null) {
            callback.onCompleted();
            callback.onEnd();
        }
    }

    @Override
    public void onNext(T response) {
        if (callback != null) {
            callback.onNext(response);
        }
    }


}