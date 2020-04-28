package com.module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.module.ui.R;
import com.module.ui.widget.HackyViewPager;

import java.util.ArrayList;

/**
 * Created by wangwei on 2018/8/10.
 * 照片浏览
 */

public class PhotoGalleryActivity extends AppCompatActivity {
    public static final String IMAGE_URL_LIST = "image_urls";
    public static final String IMAGE_POSITION = "image_index";

    private HackyViewPager mViewPager;
    private int indexPosition;
    private TextView indexView;
    private ImagePagerAdapter mAdapter;
    private ArrayList<String> urlList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_pager);
        initView();
        setListener();
        initData();


    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indexView.setText(position + 1 + " / " + mViewPager.getAdapter().getCount());

            }

        });
    }


    //跳转单张图片
    private void showOriginalPics(int position) {
        final ArrayList<String> thumbnails = new ArrayList<String>();
        thumbnails.add("http://img3.3lian.com/2013/c4/95/d/18.jpg");
        thumbnails.add("https://resources.ninghao.org/images/candy-shop.jpg");
        thumbnails.add("https://resources.ninghao.org/images/contained.jpg");
        Intent intent = new Intent(this, PhotoGalleryActivity.class);
        intent.putExtra("image_urls", thumbnails);
        intent.putExtra("image_index", position);
        Log.w("qwert", "" + position + "" + thumbnails);
        startActivity(intent);
    }

    private void initView() {
//        indexPosition = getIntent().getIntExtra("image_index", 0);
//        urlList = getIntent().getStringArrayListExtra("image_urls");
        indexPosition = 0;
        urlList.add("http://img3.3lian.com/2013/c4/95/d/18.jpg");
        urlList.add("https://resources.ninghao.org/images/candy-shop.jpg");
        urlList.add("https://resources.ninghao.org/images/contained.jpg");
        mViewPager = findViewById(R.id.pager);
        indexView = findViewById(R.id.index);
    }

    private void initData() {
        if (null != urlList && urlList.size() > 0) {

            indexView.setVisibility(urlList.size() == 1 ? View.GONE : View.VISIBLE);
            mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urlList);
            mViewPager.setAdapter(mAdapter);
            indexView.setText(1 + " / " + mViewPager.getAdapter().getCount());
            mViewPager.setCurrentItem(indexPosition);
        }
    }


    private class ImagePagerAdapter extends FragmentPagerAdapter {
        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return PhotoGallerylFragment.newInstance(url);
        }

    }
}

