package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import io.reactivex.Observable;


public class OperateFilter extends BaseOp {

    private static String TAG = "OperateFilter";

    /*
     * Filter操作符，添加指定过滤条件
     */
    public static void doSome() {
        Observable.just("a", "b", "c", "f", "ad", "c", "f", "g")
                .filter(s -> {
                    //过滤条件是长度为 1
                    return s.length() == 1;
                })
                .subscribe(getObserver(TAG, ""));
    }
}
