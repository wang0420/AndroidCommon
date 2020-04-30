package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class OperateDelay extends BaseOp {

    private static String TAG = "OperateDelay";

    /*
     * delay 操作符，推后操作，延后
     */
    public static void doSome() {
        Observable.just("abc")
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }
}
