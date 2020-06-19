package com.example.module.tablayout;


import android.os.Bundle;

import com.android.common.adapter.BaseViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员中心
 */

public class DiyTabActivity extends FragmentActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_four);
        initViews();
        initDatas();

    }


    protected void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

    }


    protected void initDatas() {
        mTitleList.add("信息表");
        mTitleList.add("承诺函");
        mTitleList.add("形象照");
        mTitleList.add("服务套餐");
        mFragmentList.add(new ListFragment());
        mFragmentList.add(new ListFragment());
        mFragmentList.add(new ListFragment());
        mFragmentList.add(new ListFragment());
        BaseViewPagerAdapter mAdapter = new BaseViewPagerAdapter(getSupportFragmentManager(), mFragmentList);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mTabLayout.setupWithViewPager(mViewPager);
        //自定义tabitem
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }


/**
 * 设置TabLayout的选中监听
 */
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.w("TAG", "onTabSelected");
                View view = tab.getCustomView();
                if (null != view) {
                    TextView textView = tab.getCustomView().findViewById(R.id.tab_item_textview);
                    textView.setTextColor(getResources().getColor(R.color.sure_red));
                    textView.setTextSize(20);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.w("TAG", "onTabUnselected");

                View view = tab.getCustomView();
                if (null != view) {
                    TextView textView = tab.getCustomView().findViewById(R.id.tab_item_textview);
                    textView.setTextColor(getResources().getColor(R.color.sure_black));
                    textView.setTextSize(13);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    ;


    /**
     * 提供TabLayout的自定义View布局
     * 根据index返回不同的View
     */
    private View getTabView(int index) {
        View view = LayoutInflater.from(DiyTabActivity.this).inflate(R.layout.layout_tab, null);
        TextView title = (TextView) view.findViewById(R.id.tab_item_textview);
        title.setText(mTitleList.get(index));
        if (index == 0) {
            title.setTextColor(getResources().getColor(R.color.sure_black));
            title.setTextSize(20);
        }
        return view;
    }


}
