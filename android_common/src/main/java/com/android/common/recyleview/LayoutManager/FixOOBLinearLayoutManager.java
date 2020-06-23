package com.android.common.recyleview.LayoutManager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FixOOBLinearLayoutManager extends LinearLayoutManager {
    public FixOOBLinearLayoutManager(Context context) {
        super(context);
    }

    public FixOOBLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public FixOOBLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
