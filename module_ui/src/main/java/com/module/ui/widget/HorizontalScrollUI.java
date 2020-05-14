package com.module.ui.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

import com.module.ui.adapter.ScrollAdapter;

import java.util.List;

/**
 * Created by wangwei on 2019/5/20.
 * 横向滚动标签View
 */

public class HorizontalScrollUI extends RecyclerView {
    private Context mContext;
    private ScrollAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    int lastClickPosition = 0;//上一次的位置

    public HorizontalScrollUI(@NonNull Context context) {
        this(context, null);
    }

    public HorizontalScrollUI(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public HorizontalScrollUI(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.setLayoutManager(linearLayoutManager);
        mAdapter = new ScrollAdapter(mContext);
        this.setAdapter(mAdapter);
        setListener();
    }


    private void setListener() {
        mAdapter.setOnItemClickLitener(new ScrollAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int index) {
//                int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                Log.w("TAG", "index---" + index + "---firstItemPosition--" + firstItemPosition +
//                        "--lastPosition--" + lastPosition);
//                Log.w("TAG", "lastClickPosition---" + lastClickPosition);
//                Log.w("TAG", "index---" + index);

                if (index > lastClickPosition) {
                    smoothScrollToPosition(index + 1);
                } else if (index != 0) {
                    smoothScrollToPosition(index - 1);
                }

                if (null != mOnTagClickLitener && lastClickPosition != index) {
                    mOnTagClickLitener.onTagClick(index);
                    lastClickPosition = index;
                }

            }
        });

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                //如果滚动到了最底部,加载更多
                int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = linearLayoutManager.getItemCount();

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void setData(List<String> mDatas) {
        mAdapter.addAll(mDatas);
        mAdapter.notifyDataSetChanged();
    }


    public interface OnTagClickLitener {
        void onTagClick(int index);
    }

    private OnTagClickLitener mOnTagClickLitener;

    public void setTagClickLitener(OnTagClickLitener mOnTagClickLitener) {
        this.mOnTagClickLitener = mOnTagClickLitener;
    }
}
