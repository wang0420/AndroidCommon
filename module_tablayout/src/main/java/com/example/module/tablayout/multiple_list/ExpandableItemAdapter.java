package com.example.module.tablayout.multiple_list;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.module.tablayout.R;
import com.example.module.tablayout.multiple_list.bean.Level0Item;
import com.example.module.tablayout.multiple_list.bean.Level1Item;
import com.example.module.tablayout.multiple_list.bean.Person;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;
    private boolean isOnlyExpandOne = true;



    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
    }


    @Override
    protected void convert(@NonNull final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                holder.setImageResource(R.id.iv_head, R.mipmap.head_img2);
                final Level0Item lv0 = (Level0Item) item;
                holder.setText(R.id.title, lv0.title)
                        .setText(R.id.sub_title, lv0.subTitle)
                        .setImageResource(R.id.iv, lv0.isExpanded() ? R.mipmap.arrow_b : R.mipmap.arrow_r);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else if (isOnlyExpandOne) {//只展开一项
                            IExpandable willExpandItem = (IExpandable) getData().get(pos);
                            for (int i = getHeaderLayoutCount(); i < getData().size(); i++) {
                                IExpandable expandable = (IExpandable) getData().get(i);
                                if (expandable.isExpanded()) {
                                    collapse(i);
                                }
                            }
                            expand(getData().indexOf(willExpandItem) + getHeaderLayoutCount());
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.title, lv1.title)
                        .setText(R.id.sub_title, lv1.subTitle);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 1 item pos: " + pos);
                       /* if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }*/
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getAdapterPosition();
                        // 先获取到当前 item 的父 positon，再移除自己
                        int positionAtAll = getParentPositionInAll(pos);
                        remove(pos);
                        if (positionAtAll != -1) {
                            IExpandable multiItemEntity = (IExpandable) getData().get(positionAtAll);
                            if (!hasSubItems(multiItemEntity)) {
                                remove(positionAtAll);
                            }
                        }
                        return true;
                    }
                });
                break;
            default:
                break;
        }
    }
}
