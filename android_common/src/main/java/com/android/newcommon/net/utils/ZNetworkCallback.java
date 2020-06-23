package com.android.newcommon.net.utils;

import com.android.newcommon.net.ZResponse;
import com.android.newcommon.net.exception.ApiException;
import com.android.newcommon.net.exception.RxErrorHandler;


public abstract class ZNetworkCallback<T extends ZResponse> extends Callback<T> {


    public abstract void onSuccess(T response);

    public abstract void onFailed(int code, String errMsg) ;


    /**
     * 剥离需要的数据返回给上层
     * 接口请求成功 并不代表真正的成功
     * 需要对业务的code 判断  再分情况返回给上层
     */
    @Override
    public void onNext(T response) {
        if (response != null) {
            if (response.code == 0) {
                onSuccess(response);
            } else {
                onFailed(response.code, response.msg);

            }
        }
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        e.printStackTrace();
        ApiException exception = new RxErrorHandler().handleError(e);//网络异常
        onFailed(exception.getCode(), exception.getMsg());

    }


}
