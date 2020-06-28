package com.android.common.base;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.common.R;
import com.android.newcommon.dialog.CustomProgressDialog;
import com.jaeger.library.StatusBarUtil;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 整个app的Activity基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected P mPresenter;
    private Unbinder mUnBinder;

    protected abstract P createPresenter();

    protected abstract int initLayout();

    protected abstract void initViews();

    protected abstract void initData(); //初始化数据


    protected CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        if (initLayout() != 0) {
            setContentView(initLayout());
            mUnBinder = ButterKnife.bind(this);
        }
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initViews();
        initData();
        int mColor = getResources().getColor(R.color.red);
        StatusBarUtil.setColor(this, mColor);

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
        if (mUnBinder != null) {
            mUnBinder.unbind();
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
