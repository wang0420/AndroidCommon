package com.moudlea.rxjava.op;

import android.util.Log;

import com.moudlea.rxjava.op.base.BaseOp;
import com.moudlea.rxjava.op.base.onNextObserver;

import java.util.List;

import io.reactivex.Observable;

public class OperateBuffer extends BaseOp {
    private static String TAG = "OperateBuffer";

    //buffer操作符，会根据指定，摘取一个list
    public static void doSome() {
        Observable.just(1, 2, 3, 54, 534, 2, 5)
                .buffer(3, 1)
                .subscribe(new onNextObserver<List<Integer>>(TAG, "") {
                    @Override
                    public void onNext(List<Integer> integers) {
                        for (Integer i : integers) {
                            Log.w(TAG, "i: " + i);
                        }
                        Log.w(TAG, "-------------------------------");
                    }
                });

    }

}
