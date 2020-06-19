package com.example.module.tablayout.multiple_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.common.ARouterManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.module.tablayout.R;
import com.example.module.tablayout.za.ZaTablayoutActivity;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
@Route(path = ARouterManager.RecyclerViewHomeActivity)
public class RecyclerViewHomeActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private HomeAdapter headerAndFooterAdapter;
    private static final int PAGE_SIZE = 3;


    private View.OnClickListener getRemoveFooterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerAndFooterAdapter.removeFooterView(v);
            }
        };
    }

    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.footer_view, (ViewGroup) mRecyclerView.getParent(), false);
        if (type == 1) {
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            imageView.setImageResource(R.mipmap.rm_icon);
        }
        view.setOnClickListener(listener);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        headerAndFooterAdapter = new HomeAdapter(getHomeItemData());
        headerAndFooterAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(headerAndFooterAdapter);

        headerAndFooterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeItem item = (HomeItem) adapter.getItem(position);
                Intent intent = new Intent(RecyclerViewHomeActivity.this, item.getActivity());
                startActivity(intent);
            }
        });


        View top = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        headerAndFooterAdapter.addHeaderView(top);

        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // headerAndFooterAdapter.addFooterView(getFooterView(1, getRemoveFooterListener()), 0);
            }
        });
        headerAndFooterAdapter.addFooterView(footerView, 0);

        mRecyclerView.setAdapter(headerAndFooterAdapter);

    }

    private List<HomeItem> getHomeItemData() {
        return Arrays.asList(
                new HomeItem("分组多Item布局", MultipleGroupActivity.class, R.mipmap.gv_databinding),
                new HomeItem("分组单Item布局", SingleGroupActivity.class, R.mipmap.gv_databinding),
                new HomeItem("多Item布局", MultipleItemQuickActivity.class, R.mipmap.gv_databinding),
                new HomeItem("Expandable折叠", ExpandableUseActivity.class, R.mipmap.gv_databinding),
                new HomeItem("拖拽", ItemDragActivity.class, R.mipmap.gv_databinding),
                new HomeItem("菜单CheckBox", SpringActivity.class, R.mipmap.gv_databinding),
                new HomeItem("ZATabLayout", ZaTablayoutActivity.class, R.mipmap.ic_launcher)

        );
    }


}
