package com.example.module.tablayout.za;

import android.os.Bundle;

import com.basemodule.adapter.CustomFragmentPagerAdapter;
import com.example.module.tablayout.R;
import com.example.module.tablayout.yangfan.modifytablayout.TestFragment;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class ZaTablayoutActivity extends AppCompatActivity {
    TabLayoutTitle tab_layout_title;
    TabLayoutIndicator tab_layout_indicator;
    ViewPager view_pager;
    private ImmersionBar mImmersionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.za_tablayout);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();


        //---------TabLayoutTitle--TabLayoutIndicator--------
        CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TestFragment(), "巴西");
        adapter.addFragment(new TestFragment(), "西班牙");
        adapter.addFragment(new TestFragment(), "阿根廷");
        adapter.addFragment(new TestFragment(), "葡萄牙");
        adapter.addFragment(new TestFragment(), "阿根廷");
        adapter.addFragment(new TestFragment(), "葡萄牙");
        adapter.addFragment(new TestFragment(), "俄罗斯");
        tab_layout_title = findViewById(R.id.tab_layout_title);
        tab_layout_indicator = findViewById(R.id.tab_layout_indicator);
        view_pager = findViewById(R.id.view_pager);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(adapter.getCount());
        tab_layout_title.setMViewPager(view_pager);
        tab_layout_indicator.setMViewPager(view_pager);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    findViewById(R.id.tab_bg).setBackgroundResource(R.drawable.bg_live_tab_party);
                    mImmersionBar.statusBarDarkFont(true).init();
                } else {
                    findViewById(R.id.tab_bg).setBackgroundResource(R.drawable.bg_live_tab_normal);
                    mImmersionBar.statusBarDarkFont(false).init();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //---------TabLayout--TabLayoutIndicator--------
        CustomFragmentPagerAdapter adapter1 = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        adapter1.addFragment(new TestFragment(), "阿根廷");
        adapter1.addFragment(new TestFragment(), "俄罗斯");
        adapter1.addFragment(new TestFragment(), "阿根廷");
        adapter1.addFragment(new TestFragment(), "俄罗斯");
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutIndicator mTabIndicator = findViewById(R.id.pager_tablay_1);
        ViewPager vp = findViewById(R.id.view_pager2);
        vp.setAdapter(adapter1);
        vp.setOffscreenPageLimit(adapter1.getCount());
        tabLayout.setupWithViewPager(vp);
        mTabIndicator.setMViewPager(vp);


    }

    /**
     * dp转换成px
     */
    public int dp2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImmersionBar = null;
    }
}
