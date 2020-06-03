package com.moudlea.rxjava.op;

import android.annotation.SuppressLint;
import android.util.Log;

import com.moudlea.rxjava.op.base.BaseOp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;

/**
 * Author: zhiwei.
 * Date: 2018/11/7 0007,20:15.
 */
public class OperateAsyncSubject extends BaseOp {
    private static String TAG = "OperateAsyncSubject";

    //    AsyncSubject 操作符，只有在observer发送complete之后，才会生效。会发送最后一个指令，以及 complete指令。
    public static void doSome() {
        AsyncSubject<String> subject = AsyncSubject.create();//todo 所有的在线Observer都只会接收到complete及之前最后一条指令
        subject.onNext("a");
        subject.onNext("b");
        subject.subscribe(getObserver(TAG, "first"));
        subject.onNext("c");
        subject.onNext("d");
        subject.onNext("e");
        subject.subscribe(getObserver(TAG, "second"));
        subject.onNext("f");
        subject.onComplete();

        /*
           first d.isDisposed():false
           second d.isDisposed():false
           first f
           first onComplete
           second f
           second onComplete
         */
    }


    // 延时3秒
    @SuppressLint("CheckResult")
    public static void delay() {
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.w("TAG", "flag--> " + aLong);
                    }
                });
    }
}
