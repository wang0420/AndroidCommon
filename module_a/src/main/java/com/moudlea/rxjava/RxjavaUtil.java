package com.moudlea.rxjava;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/6/3.
 */
public class RxjavaUtil {

    @SuppressLint("CheckResult")
    //延迟3秒
    public static void delay() {
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.w("TAG", "" + aLong);
                    }
                });

    }
}
