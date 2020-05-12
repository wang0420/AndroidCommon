package com.example.module.tablayout.multiple_list;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.FenGroupBean;
import com.example.module.tablayout.multiple_list.bean.FenGroupSection;
import com.example.module.tablayout.multiple_list.decoration.GridSectionAverageGapItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangwei on 2020/5/12.
 */

public class SingleGroupActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<FenGroupSection> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 20, 15));
        for (int i = 0; i < 10; i++) {
            mData.add(new FenGroupSection(true, "Section 1", true));//头部
            for (int j = 0; j < 6; j++) {
                //内容
                FenGroupBean bean = new FenGroupBean();
                bean.setImg("http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg");
                bean.setName("我是内容--" + j);
                mData.add(new FenGroupSection(bean));
            }

        }


        SingleGroupAdapter mAdapter = new SingleGroupAdapter(R.layout.item_text_image, R.layout.group_title_section_head, mData);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FenGroupSection mySection = mData.get(position);
                if (mySection.isHeader) {
                    //头部点击
                    Toast.makeText(SingleGroupActivity.this, "ha--" + mySection.header, Toast.LENGTH_LONG).show();
                } else {
                    //子布局点击
                    Toast.makeText(SingleGroupActivity.this, "ddd--" + mySection.t.getName(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //more  点击事件
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(SingleGroupActivity.this, "onItemChildClick" + position, Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
