package com.example.module.tablayout.multiple_list;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.FenGroupBean;
import com.example.module.tablayout.multiple_list.bean.FenGroupSection;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * author : wang wei
 * date   : 2019-12-03
 * desc   :
 */
public class SingleGroupAdapter extends BaseSectionQuickAdapter<FenGroupSection, BaseViewHolder> {

    public SingleGroupAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);


    }

    @Override
    protected void convertHead(BaseViewHolder helper, FenGroupSection item) {
        helper.setText(R.id.header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FenGroupSection item) {
        FenGroupBean video = (FenGroupBean) item.t;
        helper.setImageResource(R.id.iv, R.mipmap.m_img2);
        helper.setText(R.id.tv_content, video.getName());
        helper.addOnClickListener(R.id.tv_content);
    }
}
