package com.saas.testdemo.activitys;

/**
 * Created by wangwei on 2018/1/9.
 */

public interface RequestView extends MvpBaseView{
    void requestLoading();
    void resultSuccess(String result);
    void resultFailure(String result);
}

