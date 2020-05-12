package com.example.module.tablayout.multiple_list.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * <p>
 * 分组布局
 * 实体类必须继承SectionEntity
 */
public class FenGroupSection extends SectionEntity<FenGroupBean> {
    private boolean isMore;

    public FenGroupSection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public FenGroupSection(FenGroupBean t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }
}
