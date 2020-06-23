package com.android.newcommon.net.fileLoad.callback;

import android.text.TextUtils;
import android.widget.Toast;

import com.android.common.BaseApplication;
import com.android.newcommon.net.ZResponse;

/**

 * on 2017/5/3.
 */

public abstract class ZUploadCallback<T extends ZResponse> extends UploadCallback<T> {

    public abstract void onSuccess(T data);

    public  void onFailed(int code, String errMsg){}

    /**
     * 剥离需要的数据返回给上层
     * 接口请求成功 并不代表真正的成功
     * 需要对业务的code 判断  再分情况返回给上层
     */
    @Override
    public void onNext(T response) {
            if (response.code == 0) {
                onSuccess(response);
            } else {
                onFailed(response.code, response.msg);
            }

    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        Toast.makeText(BaseApplication.getInstance(),"出问题了",Toast.LENGTH_SHORT).show();
    }


    public void onBusinessError(int errorCode, String errorMessage) {
        if (!TextUtils.isEmpty(errorMessage)) {
            // ToastUtils.toast(BaseApplication.getContext(), errorMessage);
        }
    }



}
