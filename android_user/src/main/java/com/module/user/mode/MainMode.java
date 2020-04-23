package com.module.user.mode;

import android.widget.Toast;

import com.basemodule.BaseApplication;
import com.basemodule.base.BaseMode;
import com.basemodule.base.BaseObserver;
import com.basemodule.base.SyncCallBack;
import com.basemodule.net.RetrofitManager;
import com.module.user.bean.DataBean;
import com.module.user.service.ApiService;

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
