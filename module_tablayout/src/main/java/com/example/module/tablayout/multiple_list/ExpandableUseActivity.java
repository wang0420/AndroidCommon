package com.example.module.tablayout.multiple_list;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.basemodule.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.Level0Item;
import com.example.module.tablayout.multiple_list.bean.Level1Item;
import com.example.module.tablayout.multiple_list.bean.Person;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangwei on 2020/5/12.
 */

public class ExpandableUseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExpandableItemAdapter adapter;
    private ArrayList<MultiItemEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_item_use);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        list = generateData();
        adapter = new ExpandableItemAdapter(list);


        final GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(manager);
        Log.w("getSpanSize","setLayoutManager" );


        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                Log.w("getSpanSize",""+ adapter.getItemViewType(position)  );
                return adapter.getItemViewType(position) == ExpandableItemAdapter.TYPE_PERSON ? 1 : manager.getSpanCount();

            }
        });


        mRecyclerView.setAdapter(adapter);
       // adapter.expandAll();
    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 9;
        int lv1Count = 3;

     /*   String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();
*/
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            Level0Item lv0 = new Level0Item("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }

        return res;
    }
}
