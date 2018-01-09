package com.saas.testdemo.activitys;

/**
 * Created by wangwei on 2018/1/9.
 */

public class MvpBasePersenter<V extends MvpBaseView> {

    private V mMvpView;

    /**
     * 绑定V层
     *
     * @param view
     */
    public void attachMvpView(V view) {
        this.mMvpView = view;
    }

    /**
     * 解除绑定V层
     */
    public void detachMvpView() {
        mMvpView = null;
    }

    /**
     * 获取V层
     *
     * @return
     */
    public V getmMvpView() {
        return mMvpView;
    }
}
