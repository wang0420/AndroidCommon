package com.android.common.recyleview.LayoutManager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FixOOBGridLayoutManager extends GridLayoutManager {
    public FixOOBGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FixOOBGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FixOOBGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            // 修复 java.lang.IndexOutOfBoundsException:
            // Inconsistency detected. Invalid item position 71(offset:71).state:72
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
