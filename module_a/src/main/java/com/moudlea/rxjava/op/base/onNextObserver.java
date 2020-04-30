package com.moudlea.rxjava.op.base;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:56.
 */
public class onNextObserver<T> implements Observer<T> {
    String tag, flag;

    public onNextObserver(String tag, String flag) {
        this.tag = tag;
        this.flag = flag;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(tag, flag + " d.isDisposed():" + d.isDisposed());
    }

    @Override
    public void onNext(T s) {
    }

    @Override
    public void onError(Throwable e) {
        Log.e(tag, flag + " e:" + e);
    }

    @Override
    public void onComplete() {
        Log.i(tag, flag + " onComplete");
    }
}
