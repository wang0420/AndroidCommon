package com.example.module.tablayout.multiple_list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.StatusBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {
/*
    public HomeAdapter(int dataSize) {
        super(R.layout.item_header_and_footer, getSampleData(dataSize));
    }*/
    public HomeAdapter(List data) {
        super(R.layout.home_item_view, data);
    }



    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomeItem item) {
        helper.setText(R.id.text, item.getTitle());
        helper.setImageResource(R.id.icon, item.getImageResource());
    }


}
