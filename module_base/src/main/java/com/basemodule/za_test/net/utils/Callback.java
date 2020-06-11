package com.basemodule.za_test.net.utils;


public abstract class Callback<T> {
    public abstract void onNext(T response);

    public void onError(Throwable e) {
    }

    public void onCompleted() {
    }

    /**
     * 开始执行
     * 一定会执行
     */
    public void onBegin() {
    }

    /**
     * 执行结束
     * 一定会执行
     * 在onCompleted()或onError()后执行
     */
    public void onEnd() {
    }
}
