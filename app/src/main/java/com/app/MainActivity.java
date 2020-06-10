/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.app;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ARouterManager;
import com.basemodule.base.BaseActivity;
import com.basemodule.base.BasePresenter;
import com.basemodule.utils.PermissionUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Fragment mFragmentTab01;
    private Fragment mFragmentTab02;
    private Fragment mFragmentTab03;

    private View mTabTextView1;
    private View mTabTextView2;
    private View mTabTextView3;


    private View mTabImageView1;
    private View mTabImageView2;
    private View mTabImageView3;

    private View mLayoutTab1;
    private View mLayoutTab2;
    private View mLayoutTab3;


    private int mCurrentTabIndex = 0;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
        mTabImageView1.setSelected(true);
        mTabTextView1.setSelected(true);
    }


    @Override
    protected void initViews() {
        mLayoutTab1 = findViewById(R.id.layout_tab1);
        mLayoutTab2 = findViewById(R.id.layout_tab2);
        mLayoutTab3 = findViewById(R.id.layout_tab3);


        mTabTextView1 = findViewById(R.id.tv_tab1);
        mTabTextView2 = findViewById(R.id.tv_tab2);
        mTabTextView3 = findViewById(R.id.tv_tab3);


        mTabImageView1 = findViewById(R.id.iv_tab1);
        mTabImageView2 = findViewById(R.id.iv_tab2);
        mTabImageView3 = findViewById(R.id.iv_tab3);


        mLayoutTab1.setOnClickListener(this);
        mLayoutTab2.setOnClickListener(this);
        mLayoutTab3.setOnClickListener(this);
        initData();
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.layout_tab1) {
            setTabSelection(0);
        } else if (i == R.id.layout_tab2) {
            setTabSelection(1);
        } else if (i == R.id.layout_tab3) {
            setTabSelection(2);

        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    private void setTabSelection(int index) {
        resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                mTabImageView1.setSelected(true);
                mTabTextView1.setSelected(true);

                if (mFragmentTab01 == null) {
                    //  如果Fragment为空，则创建一个并添加到界面上
                    mFragmentTab01 = (Fragment) ARouter.getInstance().build(ARouterManager.AFragment).navigation();
                    transaction.add(R.id.id_content, mFragmentTab01);
                } else {
                    // 如果Fragment不为空，则直接将它显示出来
                    transaction.show(mFragmentTab01);
                }
                mCurrentTabIndex = 0;
                break;
            case 1:
                mTabImageView2.setSelected(true);
                mTabTextView2.setSelected(true);
                if (mFragmentTab02 == null) {
                    mFragmentTab02 = (Fragment) ARouter.getInstance().build(ARouterManager.BFragment).navigation();
                    transaction.add(R.id.id_content, mFragmentTab02);
                } else {
                    transaction.show(mFragmentTab02);
                }
                mCurrentTabIndex = 1;
                break;
            case 2:
                mTabImageView3.setSelected(true);
                mTabTextView3.setSelected(true);
                if (mFragmentTab03 == null) {
                    mFragmentTab03 = (Fragment) ARouter.getInstance().build(ARouterManager.CFragment).navigation();

                    transaction.add(R.id.id_content, mFragmentTab03);
                } else {
                    transaction.show(mFragmentTab03);
                }
                mCurrentTabIndex = 2;
                break;
        }
        transaction.commit();
    }

    /**
     * 从mFragmentTab01切换至其他Tab时，移除mFragmentTab01；目的是当从其他Tab切换回mFragmentTab01时，mFragmentTab01能实时刷新数据
     * 同时调用addToBackStack方法把mFragmentTab01放入回退栈，这样mFragmentTab01只是处于stop状态
     *
     * @param transaction
     */
    private void removeTab(FragmentTransaction transaction) {
        if (mCurrentTabIndex == 0) {
            transaction.remove(mFragmentTab01).addToBackStack(null);
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void resetBtn() {
        mTabImageView1.setSelected(false);
        mTabTextView1.setSelected(false);
        mTabImageView2.setSelected(false);
        mTabTextView2.setSelected(false);
        mTabImageView3.setSelected(false);
        mTabTextView3.setSelected(false);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mFragmentTab01 != null) {
            transaction.hide(mFragmentTab01);
        }
        if (mFragmentTab02 != null) {
            transaction.hide(mFragmentTab02);
        }
        if (mFragmentTab03 != null) {
            transaction.hide(mFragmentTab03);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFragmentTab01 != null) {
            mFragmentTab01.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}