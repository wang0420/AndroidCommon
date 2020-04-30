package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import io.reactivex.Observable;

public class OperateDistinct extends BaseOp {

    private static String TAG = "OperateDistinct";

    /*
     * distinct 自动去重复，后面若出现前面已经有的数据的时候，会自动跳过。
     */
    public static void doSome() {
        Observable.just("a", "b", "c", "f", "ad", "c", "f", "g")
                .distinct()
                .subscribe(getObserver(TAG, ""));
    }
}
