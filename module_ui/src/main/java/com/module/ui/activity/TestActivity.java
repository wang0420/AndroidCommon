package com.module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.net.NetActivity;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.module.ui.R;
import com.module.ui.activity.guide.user.GuideMainActivity;
import com.module.ui.adapter.FlexboxLayoutAdapter;
import com.module.ui.adapter.MainAdapter;
import com.module.ui.bean.UIItem;
import com.module.ui.util.DividerItemDecoration;
import com.module.ui.widget.drop_down.DropDownActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/5/19.
 */
public class TestActivity  extends AppCompatActivity {
    ArrayList<UIItem> list=new ArrayList<>();
        private RecyclerView mRecyclerView;
        private FlexboxLayoutAdapter mAdapter;
        private LinearLayoutManager linearLayoutManager;

        private List<UIItem> getUIItemData() {
            return Arrays.asList(
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜单下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜下拉菜单单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜下拉菜单单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜下拉菜单单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜下拉菜单单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜下拉菜单单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜下拉菜单单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class),
                    new UIItem("下拉菜单", DropDownActivity.class)


                    );
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ui);
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

            for (UIItem item : getUIItemData()) {
                mAdapter.addItem(item);
            }
            Log.w("TAG", "---init"+UIActivity.Companion.start("233"));
            Log.w("TAG", "---init"+UIActivity.Companion.getSaleType());
            Log.w("TAG", "---init"+UIActivity.Companion.getName());
            setListener();

        }


        private void setListener() {

            mAdapter.setOnItemClickListener(new FlexboxLayoutAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int index) {
                    UIItem item = (UIItem) mAdapter.getItem(index);
                    Intent intent = new Intent(TestActivity.this, item.getActivity());
                    startActivity(intent);
                }
            });
        }
    }

