package com.android.newcommon.net.manager;

import android.util.Log;

import com.android.newcommon.net.retrofit.BaseSubscriber;
import com.android.newcommon.net.utils.Callback;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SingleRequestManagerBuilder<T> extends ARequestManagerBuilder {
    private Observable<T> observable;

    public SingleRequestManagerBuilder api(Observable<T> observable) {
        this.observable = observable;
        return this;
    }


    public void callback(Callback<T> callback) {
        final BaseSubscriber<T> subscriber = new BaseSubscriber<>(callback);
        observable = bindRxLifecycle(observable);
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
