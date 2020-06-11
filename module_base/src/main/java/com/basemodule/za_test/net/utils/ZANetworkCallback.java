package com.basemodule.za_test.net.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.basemodule.BaseApplication;
import com.basemodule.za_test.net.ZAResponse;


public abstract class ZANetworkCallback<T extends ZAResponse> extends Callback<T> {
    @Override
    public void onNext(T response) {
        if (response != null) {
            if (response.code == 0) {
                onBusinessSuccess(response);
            } else {
                onBusinessError(response.code, response.msg);
            }
        }
    }

    public abstract void onBusinessSuccess(T response);

    public void onBusinessError(int errorCode, String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
            Toast.makeText(BaseApplication.getInstance(), errorMessage, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        Toast.makeText(BaseApplication.getInstance(), "网络异常！", Toast.LENGTH_SHORT).show();
    }

    protected void onSuperError(Throwable e) {
        super.onError(e);
    }


}
