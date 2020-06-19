package com.android.common.service;


import com.android.common.base.BasePresenter;
import com.android.common.base.SyncCallBack;
import com.android.common.bean.DataBean;
import com.android.common.iview.MainView;

public class MainPresenter extends BasePresenter<MainView, MainMode> {



    @Override
    public MainMode getModeInstance() {
        return new MainMode();
    }

    /**
     * 获取数据列表
     */
    public void getListData(int cid) {
        mView.showLoadingDialog();
        mMode.getListData(cid, new SyncCallBack<DataBean>() {

            @Override
            public void onSuccess(DataBean successData) {
                mView.getDataSuccess(successData);
                mView.dismissLoadingDialog();
            }

            @Override
            public void onError(int var1, String var2) {


            }
        });
    }
}
