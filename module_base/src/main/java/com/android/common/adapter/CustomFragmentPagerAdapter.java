package com.android.common.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * ViewPager嵌套Fragment 公用Adapter
 * 含标题
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<CharSequence> mFragmentTitleList = new ArrayList<>();

    public CustomFragmentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addFragment(Fragment fragment, CharSequence title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);

    }



    public void setTitle(int position, CharSequence title) {
        if (mFragmentTitleList.size() > position) {
            mFragmentTitleList.remove(position);
            mFragmentTitleList.add(position, title);
            notifyDataSetChanged();
        }
    }

    public void addFragmentNotify(int position, Fragment fragment, CharSequence title) {
        if (position < 0 || mFragmentList.size() == 0) {
            position = 0;
        } else if (mFragmentList.size() <= position) {
            position = mFragmentList.size() - 1;
        }
        mFragmentList.add(position, fragment);
        mFragmentTitleList.add(position, title);
        notifyDataSetChanged();
    }


    public void cleanFragment() {
        mFragmentList.clear();
        mFragmentTitleList.clear();
    }


    @Override
    public Fragment getItem(int position) {
        return (mFragmentList != null && mFragmentList.size() > position) ? mFragmentList.get(position) : null;
    }

    @Override
    public int getCount() {
        return mFragmentList != null ? mFragmentList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}
