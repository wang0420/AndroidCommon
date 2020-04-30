package com.moudlea.rxjava.op;

import com.moudlea.rxjava.op.base.BaseOp;

import io.reactivex.subjects.PublishSubject;


public class OperatePublishSubject extends BaseOp {

    private static String TAG = "OperatePublishSubject";

    /*
     *  publishSubject操作符，用于发送已经操作过的指令。并且会将后来的指令，同步发送所有在线的observer，有的操作符，只会保留一个连接。
     *  First d.isDisposed():false
        First B
        First C
        First D
        Second d.isDisposed():false
        First E
        Second E
        First F
        Second F
        First onComplete
        Second onComplete
     */
    public static void doSome() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.onNext("A");
        subject.subscribe(getObserver(TAG, "First"));
        subject.onNext("B");
        subject.onNext("C");
        subject.onNext("D");
        subject.subscribe(getObserver(TAG, "Second"));
        subject.onNext("E");
        subject.onNext("F");
        subject.onComplete();
    }

}
