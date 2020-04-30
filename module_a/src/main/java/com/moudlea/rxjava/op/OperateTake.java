package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OperateTake extends BaseOp {

    private static String TAG = "OperateTake";

    /*
     *  take操作符，摘取指定长度的数据指令，或者按时间来摘取
     */
    public static void doSome() {
        Observable.just("A", "B", "C", "D", "E", "F")
                .take(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }

}
