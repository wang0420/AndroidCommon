package com.android.common.viewpage;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 *  ViewPager嵌套Fragment 公用Adapter
 * 不含标题
 */
public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public BaseViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


}
