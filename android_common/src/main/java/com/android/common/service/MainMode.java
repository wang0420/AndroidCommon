package com.android.common.service;

import android.widget.Toast;

import com.android.common.BaseApplication;
import com.android.common.base.BaseMode;
import com.android.common.base.BaseObserver;
import com.android.common.base.SyncCallBack;
import com.android.common.bean.DataBean;
import com.android.common.net.RetrofitManager;

import io.reactivex.Observable;

/**
 * Created by wangwei on 2019/4/17.
 */

public class MainMode extends BaseMode {
    protected ApiService apiStores;


    public MainMode() {
        apiStores = RetrofitManager.getInstance().create(ApiService.class);
    }

    public void getListData(int cid, SyncCallBack callBack) {
        Observable observable = apiStores.getListData(cid);
        addSubscriptionStartRequest(observable, new BaseObserver<DataBean>() {
            @Override
            public void onSuccess(DataBean model) {
                if (null != callBack) {
                    callBack.onSuccess(model);
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(BaseApplication.getInstance(), "請求失败", Toast.LENGTH_SHORT).show();

            }

        });
    }


}
