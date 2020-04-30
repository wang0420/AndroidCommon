package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OperateScan extends BaseOp {

    private static String TAG = "OperateScan";

    /*
     *  scan操作符，向下依次遍历，类似于reduce
     */
    public static void doSome() {
        Observable.just("A", "B", "C", "D", "E", "F")
                .scan((s, s2) ->
                        //指定某个算法，依次执行运算
                        s + s2
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }

}
