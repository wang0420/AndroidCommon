package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class OperateSwitchMap extends BaseOp {

    private static String TAG = "OperateSwitchMap";

    /*
     *  switchMap 转换输入流的Observable
     */
    public static void doSome() {
        Observable.just("A", "B", "C", "D", "E", "F")
                .switchMap((Function<String, ObservableSource<String>>) s -> Observable.just(s + " @@"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(TAG, ""));
    }

}
