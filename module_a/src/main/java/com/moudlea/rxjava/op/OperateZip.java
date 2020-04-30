package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OperateZip extends BaseOp {

    private static String TAG = "OperateZip";

    /*
     *  zip操作符，合并数据操作，与concat、merge不同，这里会合并数据另为一个，且数量为最小observable的长度
     */
    public static void doSome() {
        final String[] aStrings = {"A1", "A2", "A3", "A4"};
        final String[] bStrings = {"B1", "B2", "B3"};

        final Observable<String> aObservable = Observable.fromArray(aStrings);
        final Observable<String> bObservable = Observable.fromArray(bStrings);

        Observable.zip(aObservable, bObservable, (s, s2) -> s + "--" + s2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }

}
