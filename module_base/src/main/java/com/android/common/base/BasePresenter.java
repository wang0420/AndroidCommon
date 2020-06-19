package com.android.common.base;


public abstract class BasePresenter<V extends BaseView, M extends BaseMode> {

    public V mView;
    public M mMode;
    public abstract M getModeInstance();



    /**
     * 初始化V，与P形成依赖
     * 并初始化Mode
     */
    public void attachView(V mvpView) {
        this.mView = mvpView;
        mMode = getModeInstance();
    }


    /**
     * P释放V
     */
    public void detachView() {
        this.mView = null;
        if (mMode != null) {
            mMode.onUnSubscribe();
        }
        mMode = null;
    }


}
