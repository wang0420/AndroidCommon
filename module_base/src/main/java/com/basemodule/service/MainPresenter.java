package com.basemodule.service;


import com.basemodule.base.BasePresenter;
import com.basemodule.base.SyncCallBack;
import com.basemodule.bean.DataBean;
import com.basemodule.iview.MainView;

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
