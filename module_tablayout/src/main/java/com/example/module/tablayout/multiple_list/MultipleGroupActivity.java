package com.example.module.tablayout.multiple_list;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.common.base.BaseActivity;
import com.android.common.base.BasePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.FenGroupBean;
import com.example.module.tablayout.multiple_list.bean.MultipleGroupItem;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangwei on 2020/5/12.
 * 分组多布局
 * 分组自动以不同item 组合
 */

public class MultipleGroupActivity extends BaseActivity {
    RecyclerView mRecyclerView;

    List<MultipleGroupItem> mData = new ArrayList<>();

    MultipleGroupAdapter mAdapter;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_section_uer;
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    protected void initData() {
        //我们的spanCount为4，  每个item的span size为1，
        FenGroupBean main = new FenGroupBean();
        main.setName("首页");
        main.setImg("http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg");
        mData.add(new MultipleGroupItem(MultipleGroupItem.GIRD, 1, main));
        mData.add(new MultipleGroupItem(MultipleGroupItem.GIRD, 1, main));
        mData.add(new MultipleGroupItem(MultipleGroupItem.GIRD, 1, main));
        mData.add(new MultipleGroupItem(MultipleGroupItem.GIRD, 1, main));


        mData.add(new MultipleGroupItem(MultipleGroupItem.PERSION_IMAGE_ITEM, true, "个人相册 ", true));//头部
        for (int i = 0; i < 3; i++) {

            FenGroupBean bean = new FenGroupBean();
            bean.setName("我是内容");
            bean.setImg("http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg");
            mData.add(new MultipleGroupItem(MultipleGroupItem.IMG_TEXT, 4, bean));
        }


        mData.add(new MultipleGroupItem(MultipleGroupItem.TEXT, true, "个人资料 ", true));//头部

        FenGroupBean bean = new FenGroupBean();
        bean.setName("我是内容");
        mData.add(new MultipleGroupItem(MultipleGroupItem.TEXT, 4, bean));


        mAdapter = new MultipleGroupAdapter(R.layout.group_title_section_head, mData);
        View top = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.addHeaderView(top);


        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return mData.get(position).getSpanSize();
            }
        });


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleGroupItem item = (MultipleGroupItem) adapter.getData().get(position);
                if (item.isHeader) {
                    //头部点击
                    Toast.makeText(MultipleGroupActivity.this, "ha--" + position, Toast.LENGTH_LONG).show();
                } else {
                    //子布局点击
                    Toast.makeText(MultipleGroupActivity.this, "ddd--" + position + item.t.getName(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //如果给view   helper.addOnClickListener(R.id.menu_item);  设置过  这会执行如下点击事件
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleGroupItem item = (MultipleGroupItem) adapter.getData().get(position);
                switch (item.getItemType()) {
                    case MultipleGroupItem.GIRD:
                        Log.w("TAG", "GIRD---position-----" + position);

                        break;
                    case MultipleGroupItem.TEXT:
                        Log.w("TAG", "TEXT--position-----" + position);

                        break;

                    case MultipleGroupItem.IMG_TEXT:
                        Log.w("TAG", "IMG_TEXT--position-----" + position);
                        int id = view.getId();
                        if (id == R.id.item_text_image_ll) {// 获取主体item相应数据给后期使用
                            if (item.getVideo() != null) {
                                Toast.makeText(MultipleGroupActivity.this, item.getVideo().getName(), Toast.LENGTH_LONG).show();
                            }
                        } else if (id == R.id.tv_content) {
                            Toast.makeText(MultipleGroupActivity.this, "图片下面的文字点击了", Toast.LENGTH_LONG).show();
                        }
                    default:
                        Toast.makeText(MultipleGroupActivity.this, "OnItemChildClickListener " + position, Toast.LENGTH_LONG).show();
                        break;

                }


            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


}
