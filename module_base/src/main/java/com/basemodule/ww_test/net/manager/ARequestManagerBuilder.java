package com.basemodule.ww_test.net.manager;


import com.basemodule.ww_test.net.utils.LifecycleUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;

public class ARequestManagerBuilder {
    LifecycleProvider lifecycleProvider;

    public ARequestManagerBuilder setLifecycle(LifecycleProvider lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
        return this;
    }


    protected <T> Observable<T> bindRxLifecycle(Observable<T> observable) {
        return LifecycleUtils.bindLifecycle(observable, lifecycleProvider);
    }

}
