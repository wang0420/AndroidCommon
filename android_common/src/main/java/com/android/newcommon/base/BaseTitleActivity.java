package com.android.newcommon.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.common.R;
import com.android.newcommon.utils.DisplayUtil;
import com.android.newcommon.widget.BaseTitleBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/6/10.
 */
public abstract class BaseTitleActivity extends AppCompatActivity {
    private Unbinder mUnBinder;
    private BaseTitleBar baseTitleBar;
    protected View mContentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        //用户打印子类的类名
        Log.i("ZACurrentActivity", "-----" + this.getClass().getSimpleName());
        setContentView(R.layout.activity_base);
        initBaseView();
        initView();
        initData();
    }

    /**
     * 初始化根布局
     */
    protected void initBaseView() {
        baseTitleBar = find(R.id.title_bar);
        if (layoutResID() != 0) {
            mContentLayout = LayoutInflater.from(this).inflate(layoutResID(), null);
        }
        addViewToRootLayout(mContentLayout);
        if (layoutResID() != 0) {
            mUnBinder = ButterKnife.bind(this);
        }
    }

    /**
     * 将view添加到布局
     * 目的将当前界面的布局添加到BaseActivity中去
     */
    protected void addViewToRootLayout(View view) {
        if (view.getParent() == null) {
            ViewGroup rootLayout = find(R.id.base_root_layout);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.topMargin = DisplayUtil.getTitlebarHeight(getActivity());
            if (baseTitleBar != null && baseTitleBar.getVisibility() == View.GONE) {
                layoutParams.topMargin = 0;
            }
            rootLayout.addView(view, 0, layoutParams);
        }
    }


    public abstract void initView();

    public abstract void initData();

    public abstract int layoutResID();


    @SuppressWarnings("unchecked")
    protected <T extends View> T find(int id) {
        return (T) findViewById(id);
    }

    /**
     * 动态设置titleBar 是否可见
     *
     * @param isVisible
     */
    public void setTitleBarVisible(boolean isVisible) {
        baseTitleBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mContentLayout.getLayoutParams();
        params.topMargin = isVisible ? DisplayUtil.getTitlebarHeight(getActivity()) : 0;
        mContentLayout.setLayoutParams(params);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }


    @Override
    public void setTitle(int titleId) {
        baseTitleBar.setTitleText(titleId);
    }

    /**
     * 设置title
     *
     * @param title
     */
    public void setTitle(String title) {
        baseTitleBar.setTitleText(title);
    }


    /**
     * 获取titleBar
     *
     * @return
     */
    public BaseTitleBar getBaseTitleBar() {
        return baseTitleBar;
    }


    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, R.anim.slide_right_in, R.anim.slide_left_out);
    }

    public void startActivity(Intent intent, int enterAnim, int exitAnim) {
        super.startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }


    @Override
    public void finish() {
        finish(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void finish(int enterAnim, int exitAnim) {
        super.finish();
        overridePendingTransition(enterAnim, exitAnim);
    }


    protected Activity getActivity() {
        return this;
    }

}
