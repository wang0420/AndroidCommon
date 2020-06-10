package com.basemodule.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.R;
import com.basemodule.utils.SoftInputManager;

import java.lang.reflect.Method;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/6/10.
 */
public abstract class NewBaseActivity extends AppCompatActivity {
    LinearLayout mRootBaseView;//根布局
    View titleView;//include的titleBar
    TextView tvLeft;//titleBar左边的返回键
    TextView tvMiddle;//titleBar的标题

    View mStateLayout;//include的state_layout
    LinearLayout ll_page_state_error;//stateLayout网络错误的布局
    LinearLayout ll_page_state_empty;//stateLayout无数据的布局
    Button btReload;//网络错误重新加载的布局

    /**
     * 顶层layout
     */
    protected View mBaseView;
    /**
     * 内容layout
     */
    protected View mContentLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //用户打印子类的类名
        Log.i("ZACurrentActivity", "---1---" + this.getClass().getSimpleName());
        mBaseView = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        if (mBaseView != null) {
            //注意：这里的setContentView必须有才可以，需要走父类方法
            setContentView(mBaseView);
        }
        initBaseViews();
        init();

        initViewData();
    }
    public abstract void init();
    public abstract void initViewData();

    /**
     * 布局
     *
     * @return 返回布局
     */
    public abstract int getLayoutId();


    protected void initBaseViews() {
        Log.i("ZACurrentActivity", "---4---");
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            mContentLayout = LayoutInflater.from(this).inflate(layoutId, null);
            addViewToRootLayout(mContentLayout);
        }

        mBaseView = find(R.id.activity_base_root);
        mRootBaseView = (LinearLayout) findViewById(R.id.activity_base_root);
        titleView = findViewById(R.id.activity_base_title_bar);
        tvLeft = (TextView) findViewById(R.id.title_bart_tv_left);
        tvMiddle = (TextView) findViewById(R.id.title_bart_tv_middle);
        mStateLayout = findViewById(R.id.activity_base_state_layout);
        btReload = (Button) findViewById(R.id.state_layout_error_bt);
        ll_page_state_empty = (LinearLayout) findViewById(R.id.state_layout_empty);
        ll_page_state_error = (LinearLayout) findViewById(R.id.state_layout_error);
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftInputManager.hideSoftInput(NewBaseActivity.this);
                finish();
            }
        });
    }

    /**
     * 切换页面的布局
     *
     * @param pageState 页面状态 NORMAL  EMPTY  ERROR
     */
    public void changePageState(PageState pageState) {
        switch (pageState) {
            case NORMAL:
                if (mStateLayout.getVisibility() == View.VISIBLE) {
                    mStateLayout.setVisibility(View.GONE);
                }
                break;
            case ERROR:
                if (mStateLayout.getVisibility() == View.GONE) {
                    mStateLayout.setVisibility(View.VISIBLE);
                    ll_page_state_error.setVisibility(View.VISIBLE);
                    btReload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // reloadInterface.reloadClickListener();
                        }
                    });
                    ll_page_state_empty.setVisibility(View.GONE);
                }
                break;
            case EMPTY:
                if (mStateLayout.getVisibility() == View.GONE) {
                    mStateLayout.setVisibility(View.VISIBLE);
                    ll_page_state_error.setVisibility(View.GONE);
                    ll_page_state_empty.setVisibility(View.VISIBLE);
                }
                break;
        }


    }

    public enum PageState {
        /**
         * 数据内容显示正常
         */
        NORMAL,
        /**
         * 数据为空
         */
        EMPTY,
        /**
         * 数据加载失败
         */
        ERROR
    }


    /**
     * 设置背景颜色和背景
     *
     * @param colorRes
     */
    public void setBackgroundColor(int colorRes) {
        if (mBaseView != null) {
            mBaseView.setBackgroundColor(ContextCompat.getColor(this, colorRes));
        }
    }


    /**
     * 将view添加到布局
     * 目的将当前界面的布局添加到BaseActivity中去
     */
    protected void addViewToRootLayout(View mContentLayout) {
        Log.i("ZACurrentActivity", "---6---");
        if (mContentLayout.getParent() == null) {
            ViewGroup rootLayout = find(R.id.activity_base_root);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            rootLayout.addView(mContentLayout, layoutParams);

        }
    }

    /**
     * 移除布局
     *
     * @param view
     */
    private void removeViewFromRootLayout(View view) {
        if (mBaseView != null && mBaseView instanceof ViewGroup) {
            ViewGroup rootLayout = (ViewGroup) mBaseView;
            rootLayout.removeView(view);
        }
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T find(int id) {
        return (T) findViewById(id);
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


    public Context getContext() {
        return this;
    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }


}
