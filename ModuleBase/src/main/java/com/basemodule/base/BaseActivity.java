package com.basemodule.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.basemodule.widget.CustomProgressDialog;


/**
 * 整个app的Activity基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected P mPresenter;

    protected abstract P createPresenter();

    protected CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog("", true);
    }

    @Override
    public void showLoadingDialog(String text) {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(text);
        } else {
            showLoadingDialog(text, true);
        }
    }

    protected void showLoadingDialog(String text, boolean cancelEnable) {
        if (isFinishing()) {
            return;
        }
        mProgressDialog = new CustomProgressDialog(this);
        mProgressDialog.setCancelable(cancelEnable);
        mProgressDialog.setCanceledOnTouchOutside(cancelEnable);
        mProgressDialog.setMessage(text);
        mProgressDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


}
