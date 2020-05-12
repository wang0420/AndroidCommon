package com.example.module.tablayout.multiple_list;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.module.tablayout.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangwei on 2020/5/12.
 */

public class ItemDragActivity  extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter1 mAdapter;
    List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        for (int i = 0; i < 10; i++){
            mDatas.add("Item" + i);
        }
        mAdapter = new RecyclerViewAdapter1(mDatas);
        //拖拽滑动功能
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CustomItemTouchCallback(mAdapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

    }


}
