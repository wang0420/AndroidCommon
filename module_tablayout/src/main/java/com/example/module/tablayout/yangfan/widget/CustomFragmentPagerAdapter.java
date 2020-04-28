package com.example.module.tablayout.yangfan.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<CharSequence> mFragmentTitleList = new ArrayList<>();

    public CustomFragmentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return (mFragmentList != null && mFragmentList.size() > position) ? mFragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    public void addFrag(Fragment fragment, CharSequence title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void cleanFrag() {
        mFragmentList.clear();
        mFragmentTitleList.clear();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}
