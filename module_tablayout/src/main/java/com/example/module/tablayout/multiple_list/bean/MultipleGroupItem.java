package com.example.module.tablayout.multiple_list.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * 分组布局
 * 实体类必须继承SectionMultiEntity
 */
public class MultipleGroupItem extends SectionMultiEntity<FenGroupBean> implements MultiItemEntity {

    public static final int TEXT = 1;
    public static final int GIRD = 2;
    public static final int IMG_TEXT = 3;
    public static final int HEART_CARD_ITEM = 4;
    public static final int VAULE_QUESTION_ITEM = 5;
    public static final int BASE_DATA_INFO = 6;
    public static final int PERSION_IMAGE_ITEM = 7;//个人相册
    public static final int IEEA_LIFE_ITEM = 8;//理想生活


    private int itemType;
    private int spanSize;
    private boolean isMore;
    private FenGroupBean video;

    public MultipleGroupItem(int itemType, boolean isHeader, String header, boolean isMore) {
        super(isHeader, header);
        this.isMore = isMore;
        this.itemType = itemType;
    }

    public MultipleGroupItem(int itemType, int spanSize, FenGroupBean video) {
        super(video);
        this.video = video;
        this.itemType = itemType;
        this.spanSize = spanSize;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public FenGroupBean getVideo() {
        return video;
    }

    public void setVideo(FenGroupBean video) {
        this.video = video;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
