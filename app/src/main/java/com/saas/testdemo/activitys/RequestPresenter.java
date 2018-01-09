package com.saas.testdemo.activitys;

import android.os.Handler;

/**
 * @description
 */
public class RequestPresenter extends MvpBasePersenter<RequestView> {
    public void clickRequest(final String cityId) {
        //获取View
        if (getmMvpView() != null) {
            getmMvpView().requestLoading();
        }
        //模拟网络延迟，可以显示出加载中
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getmMvpView().resultSuccess("成功");

            }
        }, 2000);
    }

    public void interruptHttp() {
        // mRequestMode.interruptHttp();
    }
}
