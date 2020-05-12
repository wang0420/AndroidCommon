package com.example.module.tablayout.multiple_list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.MultipleGroupItem;
import com.example.module.tablayout.multiple_list.bean.MultipleItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangwei on 2020/5/12.
 */

public class MultipleItemQuickActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        List<MultipleItem> list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(new MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE));
            list.add(new MultipleItem(MultipleItem.TEXT, MultipleItem.TEXT_SPAN_SIZE, "wang"));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
            list.add(new MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN));
        }

        final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(this, list);
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);
        multipleItemAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                Log.w("getSpanSize",""+list.get(position).getSpanSize() );

                return list.get(position).getSpanSize();
            }
        });
        mRecyclerView.setAdapter(multipleItemAdapter);

        multipleItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleItem item = (MultipleItem) adapter.getData().get(position);

                switch (item.getItemType()) {
                    case MultipleItem.IMG_TEXT:
                        Toast.makeText(MultipleItemQuickActivity.this, "IMG_TEXT" + position, Toast.LENGTH_SHORT).show();

                        break;
                    case MultipleItem.TEXT:
                        Toast.makeText(MultipleItemQuickActivity.this, "TEXT" + position, Toast.LENGTH_SHORT).show();

                        break;

                    case MultipleItem.IMG:
                        int id = view.getId();
                        Toast.makeText(MultipleItemQuickActivity.this, "IMG" + position, Toast.LENGTH_SHORT).show();

                        break;

                }
            }
        });
    }
}
