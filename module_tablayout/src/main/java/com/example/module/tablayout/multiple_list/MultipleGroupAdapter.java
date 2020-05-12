package com.example.module.tablayout.multiple_list;


import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.MultipleGroupItem;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * author : wang wei
 * date   : 2019-12-03
 * desc   :
 */
public class MultipleGroupAdapter extends BaseSectionMultiItemQuickAdapter<MultipleGroupItem, BaseViewHolder> {


    public MultipleGroupAdapter(int sectionHeadResId, List data) {
        super(sectionHeadResId, data);
        addItemType(MultipleGroupItem.GIRD, R.layout.item_text_group);
        addItemType(MultipleGroupItem.TEXT, R.layout.item_text_item);
        addItemType(MultipleGroupItem.IMG_TEXT, R.layout.item_text_image);


    }

    @Override
    protected void convertHead(BaseViewHolder helper, MultipleGroupItem item) {
        // deal with header viewHolder
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
       // helper.setVisible(R.id.card_view, false);


       // helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultipleGroupItem item) {
        // deal with multiple type items viewHolder
        helper.addOnClickListener(R.id.menu_item);
        helper.addOnClickListener(R.id.item_text_image_ll);
        helper.addOnClickListener(R.id.tv_content);




        switch (helper.getItemViewType()) {
            case MultipleGroupItem.GIRD:
                helper.setImageResource(R.id.icon, R.mipmap.girl);
                break;
            case MultipleGroupItem.TEXT:
                helper.setText(R.id.tv, item.getVideo().getName());
                break;

            case MultipleGroupItem.IMG_TEXT:
                helper.setImageResource(R.id.iv, R.mipmap.animation_img2);
            default:
                break;

        }
    }
}
