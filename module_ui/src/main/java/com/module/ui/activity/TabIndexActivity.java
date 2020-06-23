package com.module.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.common.viewpage.BaseViewPagerAdapter;
import com.module.ui.R;
import com.module.ui.fragment.GuiFragment;
import com.module.ui.fragment.QuFragment;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class TabIndexActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Fragment> fragmentList;
    private TextView one, two;
    private int mCurrentPosition = 0;
    GuiFragment fragment1;
    QuFragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tab_index);
        initView();
        initViewPager();
    }

    private void initView() {
        two = (TextView) findViewById(R.id.two);
        one = (TextView) findViewById(R.id.one);
        one.setOnClickListener(new txListener(0));
        two.setOnClickListener(new txListener(1));
    }

    // Tab的监听事件
    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mViewPager.setCurrentItem(index);
            Log.w("onClick==", "" + index);
        }
    }

    private void resetBtn() {
        two.setSelected(false);
        one.setSelected(false);
    }

    private void changeTypeAndTab() {
        resetBtn();
        switch (mCurrentPosition) {
            case 0:
                one.setSelected(true);
                break;
            case 1:
                two.setSelected(true);
                break;

        }
    }

    /*
     * 初始化ViewPager
     */
    public void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentList = new ArrayList<Fragment>();
        // fragment

        fragment1 = new GuiFragment();
        fragment2 = new QuFragment();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        mViewPager.setOffscreenPageLimit(1);
        // 给ViewPager设置适配器
        mViewPager.setAdapter(new BaseViewPagerAdapter(getSupportFragmentManager(), fragmentList));
        mViewPager.setCurrentItem(0);// 设置当前显示标签页为第一页
        one.setSelected(true);
        // 页面变化时的监听器
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                changeTypeAndTab();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


}
