package com.module.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.module.ui.R;
import com.module.ui.adapter.FlexboxLayoutAdapter;
import com.module.ui.bean.UIItem;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/5/19.
 */
public class TestActivity extends AppCompatActivity {
    ArrayList<UIItem> list = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private FlexboxLayoutAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_layout);
        mRecyclerView = findViewById(R.id.rv_list);
           /* linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration());
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());    // 设置item动画
            mAdapter = new FlexboxLayoutAdapter(this);
            mRecyclerView.setAdapter(mAdapter);*/

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP); //设置是否换行
        layoutManager.setFlexDirection(FlexDirection.ROW); // 设置主轴排列方式
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FlexboxLayoutAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        for (int i = 0; i < 18; i++) {
            mAdapter.addItem("FlexboxLayout" + i * i* i* i);
        }
        Log.w("TAG", "---init" + UIActivity.Companion.start("233"));
        Log.w("TAG", "---init" + UIActivity.Companion.getSaleType());
        Log.w("TAG", "---init" + UIActivity.Companion.getName());
        setListener();

    }


    private void setListener() {

        mAdapter.setOnItemClickListener(new FlexboxLayoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int index) {

            }
        });
    }
}

