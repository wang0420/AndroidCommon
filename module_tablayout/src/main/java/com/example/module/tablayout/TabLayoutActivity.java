package com.example.module.tablayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by wangwei on 2016/9/9.
 */
public class TabLayoutActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    ViewPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_four);
        findViews();

    }

    private void findViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mFragmentList.add(new DemoFragment());
        mFragmentList.add(new DemoFragment());
        mFragmentList.add(new DemoFragment());
        mFragmentTitleList.add("第一个");
        mFragmentTitleList.add("第二个");
        mFragmentTitleList.add("第三个");
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

//自定义tabitem
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mAdapter.getTabView(i));
            }
        }
        mViewPager.setCurrentItem(2);
        //mTabLayout.getTabAt(2).getCustomView().setSelected(true);

    }

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private Context mContext;

        public ViewPagerAdapter(FragmentManager fragmentManager, Context mContext) {
            super(fragmentManager);
            this.mContext = mContext;
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.tab_item, null);
            TextView tv = (TextView) v.findViewById(R.id.news_title);
            tv.setText(getPageTitle(position));
            ImageView img = (ImageView) v.findViewById(R.id.imageView);
            //img.setImageResource(imageResId[position]);
            return v;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
