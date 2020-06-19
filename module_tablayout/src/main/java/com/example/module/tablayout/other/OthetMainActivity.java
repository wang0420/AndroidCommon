package com.example.module.tablayout.other;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.android.common.utils.GlideUtils;
import com.example.module.tablayout.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by wangwei on 2018/2/1.
 * https://github.com/flavioarfaria/KenBurnsView
 */

public class OthetMainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    CollapsingToolbarLayout mCollapsingToolbar;
    AppBarLayout mAppBarLayout;
    TabLayout mTabLayout;
    TextView mToolbarTextView;
    KenBurnsView mHeaderImageView;

    ViewPager mViewPager;

    ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        findViews();
        setUpViews();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTextView = (TextView) findViewById(R.id.toolbar_title);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mHeaderImageView = (KenBurnsView) findViewById(R.id.imageView_header);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHeaderImageView != null) {
            mHeaderImageView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHeaderImageView != null) {
            mHeaderImageView.pause();
        }
    }

    private void setUpViews() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // mCollapsingToolbar.setTitleEnabled(false);
        mCollapsingToolbar.getLayoutParams().height =
                (Utils.isLand(this) ? Utils.getDisplayDimen(this).y :
                        Utils.getDisplayDimen(this).x) * 9 / 16 +
                        Utils.getStatusBarHeightPixel(this);


        mCollapsingToolbar.requestLayout();

        // TODO : Hack for CollapsingToolbarLayout
        mToolbarTextView.setText("Demo");
        actionBarResponsive();
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (mToolbarTextView != null) {
                    if (state == State.COLLAPSED) {
                        Log.w("TAG", "shang");
                        if (Build.VERSION.SDK_INT >= 11) {
                            mToolbarTextView.setAlpha(1);
                        } else {
                            setAlphaForView(mToolbarTextView, 1);
                        }
//                        mCollapsingToolbar.setContentScrimColor(
//                                ContextCompat.getColor(DiyInterActivity.this, R.color.colorPrimary));
                    } else if (state == State.EXPANDED) {
                        Log.w("TAG", "xia");
                        if (Build.VERSION.SDK_INT >= 11) {
                            mToolbarTextView.setAlpha(0);
                        } else {
                            setAlphaForView(mToolbarTextView, 0);
                        }
//                        mCollapsingToolbar.setContentScrimColor(ContextCompat
//                                .getColor(DiyInterActivity.this, android.R.color.transparent));
                    }
                }
            }

            @Override
            public void onOffsetChanged(State state, float offset) {
                if (mToolbarTextView != null) {
                    if (state == State.IDLE) {
                        if (Build.VERSION.SDK_INT >= 11) {
                            mToolbarTextView.setAlpha(offset);
//                            mCollapsingToolbar.setContentScrimColor(
//                                    (int) new android.animation.ArgbEvaluator().evaluate(offset,
//                                            ContextCompat.getColor(DiyInterActivity.this,
//                                                    android.R.color.transparent), ContextCompat
//                                                    .getColor(DiyInterActivity.this,
//                                                            R.color.colorPrimary)));
                        } else {
                            setAlphaForView(mToolbarTextView, offset);
//                            mCollapsingToolbar.setContentScrimColor((int) new ArgbEvaluator()
//                                    .evaluate(offset, ContextCompat.getColor(DiyInterActivity.this,
//                                            android.R.color.transparent), ContextCompat
//                                            .getColor(DiyInterActivity.this, R.color.colorPrimary)));
                        }
                    }
                }
            }
        });

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        GlideUtils.loadImage(OthetMainActivity.this, mHeaderImageView, "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                        break;
                    case 1:
                        GlideUtils.loadImage(OthetMainActivity.this, mHeaderImageView,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg"
                        );
                        break;
                    case 2:
                        GlideUtils.loadImage(OthetMainActivity.this, mHeaderImageView,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg"
                        );
                        break;
                    case 3:
                        GlideUtils.loadImage(OthetMainActivity.this, mHeaderImageView,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setAlphaForView(View v, float alpha) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void actionBarResponsive() {
        int actionBarHeight = Utils.getActionBarHeightPixel(this);
        int tabHeight = Utils.getTabHeight(this);
        if (actionBarHeight > 0) {
            mToolbar.getLayoutParams().height = actionBarHeight + tabHeight;
            mToolbar.requestLayout();
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new DemoFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Demo " + position;
        }
    }
}
