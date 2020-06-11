package com.basemodule.ww_test.net.manager;

import android.util.Log;

import com.basemodule.ww_test.net.retrofit.BaseSubscriber;
import com.basemodule.ww_test.net.utils.Callback;
import com.basemodule.ww_test.net.utils.LifecycleUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SingleRequestManagerBuilder<T> extends ARequestManagerBuilder {
    private Observable<T> observable;
    private LifecycleProvider lifecycleProvider;

    public SingleRequestManagerBuilder api(Observable<T> observable) {
        this.observable = observable;
        return this;
    }

    public void setLifecycle(LifecycleProvider lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }


    public void callback(Callback<T> callback) {
        final BaseSubscriber<T> subscriber = new BaseSubscriber<>(callback);
        observable = LifecycleUtils.bindLifecycle(observable, lifecycleProvider);
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(
                        new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                Log.w("TAG", "Unsubscribing subscription from onCreate()" + disposable.isDisposed());
                                if (disposable.isDisposed()) {
                                    return;
                                }
                                subscriber.onBegin();
                            }
                        }
                )
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
