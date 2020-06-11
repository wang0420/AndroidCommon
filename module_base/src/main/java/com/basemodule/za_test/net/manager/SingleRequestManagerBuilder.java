package com.basemodule.za_test.net.manager;

import com.basemodule.za_test.net.retrofit.BaseSubscriber;
import com.basemodule.za_test.net.utils.Callback;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SingleRequestManagerBuilder<T> extends ARequestManagerBuilder {
    private Observable<T> observable;
    private int mRetryCount = 3;

    public SingleRequestManagerBuilder api(Observable<T> observable) {
        this.observable = observable;
        return this;
    }
    public SingleRequestManagerBuilder setRetryCount(int retryCount) {
        this.mRetryCount = retryCount;
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
    @Override
    public SingleRequestManagerBuilder<T> isBlock(boolean isBlock) {
        super.isBlock(isBlock);
        return this;
    }

    public void callback(Callback<T> callback) {
        final BaseSubscriber<T> subscriber = new BaseSubscriber<>(callback);
        observable = bindRxLifecycle(observable);
        if (isBlock) {
            observable.doOnSubscribe(
                    new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            if (disposable.isDisposed()) {
                                return;
                            }
                            subscriber.onBegin();
                        }
                    })
                    .subscribe(subscriber);
        } else {
            observable.subscribeOn(Schedulers.io())
                    .doOnSubscribe(
                            new Consumer<Disposable>() {
                                @Override
                                public void accept(Disposable disposable) throws Exception {
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
}
