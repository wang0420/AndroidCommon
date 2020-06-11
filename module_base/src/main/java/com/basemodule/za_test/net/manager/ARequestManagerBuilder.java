package com.basemodule.za_test.net.manager;

import com.basemodule.za_test.net.utils.LifecycleUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;


public class ARequestManagerBuilder {
    LifecycleProvider lifecycleProvider;
    protected boolean isBlock = false;

    public ARequestManagerBuilder setLifecycle(LifecycleProvider lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
        return this;
    }

    /**
     * 是否阻塞当前线程
     * true 在当前线程执行
     * false 异步线程执行
     *
     * @param isBlock 是否阻塞当前线程
     * @return ARequestManagerBuilder
     */
    public ARequestManagerBuilder isBlock(boolean isBlock) {
        this.isBlock = isBlock;
        return this;
    }


    protected <T> Observable<T> bindRxLifecycle(Observable<T> observable) {
        return LifecycleUtils.bindLifecycle(observable, lifecycleProvider);
    }

}
