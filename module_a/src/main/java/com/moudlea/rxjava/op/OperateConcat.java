package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import io.reactivex.Observable;


public class OperateConcat extends BaseOp {

    private static String TAG = "OperateConcat";

    /*
     * concat组合操作符，会保留原有的顺序，组合成一个observable发布
     */
    public static void doSome() {
        final String[] aStrings = {"A1", "A2", "A3", "A4"};
        final String[] bStrings = {"B1", "B2", "B3"};

        final Observable<String> aObservable = Observable.fromArray(aStrings);
        final Observable<String> bObservable = Observable.fromArray(bStrings);

        Observable.concat(aObservable, bObservable)
                .subscribe(getObserver(TAG, ""));
    }

}
