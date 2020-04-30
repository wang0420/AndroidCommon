package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OperateThrottleFirst extends BaseOp {

    private static String TAG = "OperateThrottleFirst";

    /*
     * ThrottleFirst操作符，会在指定的时间间隔之内，发送第一条指令
     */
    public static void doSome() {
        getObservable()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }

    /*
        d.isDisposed():false
        a
        c
        e
        onComplete
     */
    private static Observable<String> getObservable() {
        return Observable.create(emitter -> {
            // send events with simulated time wait
            emitter.onNext("a"); // skip
            Thread.sleep(400);
            emitter.onNext("b"); // deliver
            Thread.sleep(505);
            emitter.onNext("c"); // skip
            Thread.sleep(100);
            emitter.onNext("d"); // deliver，3和4属于同一个500ms间隔
            Thread.sleep(605);
            emitter.onNext("e"); // deliver
            Thread.sleep(510);
            emitter.onComplete();
        });
    }

}
