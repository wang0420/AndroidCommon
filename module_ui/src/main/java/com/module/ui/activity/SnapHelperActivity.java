package com.module.ui.activity;

import android.os.Bundle;

import com.module.ui.R;
import com.module.ui.adapter.SnapHelperAdapter;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

//LinearSnapHelper 类的目的是将某个View停留在正中间，我们也可以通过这种方式来实现每次滑动结束之后将某个View停留在最左边或者最右边。
public class SnapHelperActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ArrayList<String> mData;
    LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_helper);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initData();
        Random r = new Random();
        int type = r.nextInt(2);//获取[0, 2)之间的int整数
        mRecyclerView.setAdapter(new SnapHelperAdapter(this, mData,type));
        //LinearSnapHelper：使当前Item居中显示，常用场景是横向的RecyclerView, 类似ViewPager效果，但是又可以快速滑动多个条目。
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
        //PagerSnapHelper：使RecyclerView 像ViewPager一样的效果，每次只能滑动一页。
        // new PagerSnapHelper().attachToRecyclerView(mRecyclerView);
        // new GallerySnapHelper().attachToRecyclerView(mRecyclerView);
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            mData.add("i=" + i);
        }
    }

}
