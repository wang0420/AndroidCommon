package com.example.module.tablayout.multiple_list;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.MultipleItem;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by wangwei on 2020/5/12.
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter(Context context,List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.item_text_view);
        addItemType(MultipleItem.IMG, R.layout.item_image_view);
        addItemType(MultipleItem.IMG_TEXT, R.layout.item_img_text_view);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultipleItem item) {
        helper.addOnClickListener(R.id.card_view);
        helper.addOnClickListener(R.id.card_view_image);
        helper.addOnClickListener(R.id.card_view_image_text);
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                helper.setText(R.id.tv, item.getContent());
                break;
            case MultipleItem.IMG_TEXT:
                helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
                break;
            default:
                break;
        }
    }



}
