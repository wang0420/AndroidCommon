package com.example.module.tablayout.multiple_list;

/**
 * Created by wangwei on 2020/5/12.
 */

public interface ItemTouchStatus {

    boolean onItemMove(int fromPosition, int toPosition);

    boolean onItemRemove(int position);
}
