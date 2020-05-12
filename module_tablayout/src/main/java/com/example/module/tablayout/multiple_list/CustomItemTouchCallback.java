package com.example.module.tablayout.multiple_list;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangwei on 2020/5/12.
 */

public class CustomItemTouchCallback extends ItemTouchHelper.Callback {

    private final ItemTouchStatus mItemTouchStatus;

    public CustomItemTouchCallback(ItemTouchStatus itemTouchStatus) {
        mItemTouchStatus = itemTouchStatus;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
       //在此方法里面我们需要构建两个flag，一个是dragFlags，表示拖动效果支持的方向，另一个是swipeFlags，
        // 上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT;
        // 向左滑动
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
       //当拖动效果已经产生了，会回调此方法。在此方法里面，我们通常会更新数据源，就比如说，一个ItemView从0拖到了1位置，那么对应的数据源也需要更改位置。
        // 交换在数据源中相应数据源的位置
        return mItemTouchStatus.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //当侧滑效果以上产生了，会回调此方法。在此方法里面，我们也会更新数据源。与onMove方法不同到的是，我们在这个方法里面从数据源里面移除相应的数据，然后调用notifyXXX方法就行了。
        // 从数据源中移除相应的数据
        mItemTouchStatus.onItemRemove(viewHolder.getAdapterPosition());
    }
}
