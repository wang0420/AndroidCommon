package com.basemodule.base;


import android.util.Log;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 剥离数据的观察者
 */
public abstract class BaseObserver<M> extends DisposableObserver<BaseRespond<M>> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);


    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            Log.d("TAG", "code=" + code);
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg);
        } else {
            onFailure(throwable.getMessage());
        }

    }

    /**
     * 剥离需要的数据返回给上层
     * 接口请求成功 并不代表真正的成功
     * 需要对业务的code 判断  再分情况返回给上层
     */
    @Override
    public void onNext(BaseRespond<M> mBaseRespond) {
        onSuccess(mBaseRespond.getData());
    }


    @Override
    public void onComplete() {

    }
}
