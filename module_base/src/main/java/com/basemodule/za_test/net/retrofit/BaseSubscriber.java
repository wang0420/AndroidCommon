package com.basemodule.za_test.net.retrofit;

import android.util.Log;

import com.basemodule.za_test.net.utils.Callback;

import io.reactivex.observers.DefaultObserver;


public class BaseSubscriber<T> extends DefaultObserver<T> {

    private Callback<T> callback;


    public BaseSubscriber(Callback<T> callback) {
        this.callback = callback;
    }

    public void onBegin() {
        if (callback != null) {
            callback.onBegin();
            Log.w("TAG", "---Started onBegin ");

        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (callback != null) {
            callback.onError(e);
            callback.onEnd();
        }
        Log.w("TAG", "---Started onError ");

    }

    @Override
    public void onComplete() {
        if (callback != null) {
            callback.onCompleted();
            callback.onEnd();
        }
        Log.w("TAG", "---Started onComplete ");

    }

    @Override
    public void onNext(T response) {
        if (callback != null) {
            callback.onNext(response);
        }
        Log.w("TAG", "---Started onNext ");

    }


}