package com.basemodule.ww_test.net.fileLoad.callback;

import android.text.TextUtils;

import com.basemodule.ww_test.net.ZResponse;

/**
 * Created by XingjieZheng
 * on 2017/5/3.
 */

public abstract class ZUploadCallback<T extends ZResponse> extends UploadCallback<T> {
    @Override
    public void onNext(T response) {
        if (response != null) {
           /* if (!response.isError) {
                onBusinessSuccess(response);
            } else {
                onBusinessError(response.errorCode, response.errorMessage);
            }*/
        }
    }

    public abstract void onBusinessSuccess(T response);

    public void onBusinessError(String errorCode, String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
           // ToastUtils.toast(BaseApplication.getContext(), errorMessage);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
       // ToastUtils.toast(BaseApplication.getContext(), R.string.no_network_connected);
    }


}
