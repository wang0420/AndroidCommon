package com.android.newcommon.recyleview.adapter;


import android.view.View;
import android.view.ViewGroup;


import com.android.newcommon.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 * 封装RecyclerView 通用的Adapter
 * @author wangwei
 * @date 2020/7/11.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 99;
    private ArrayList<T> mData = new ArrayList<>();
    private View mHeaderView;
    private int mHeaderCount = 0; //头部View个数

    public void setDataList(ArrayList<T> dataList) {
        mData = dataList;
        notifyDataSetChanged();
    }

    public void addItem(T data) {
        mData.add(data);
    }

    public void addAll(List<T> data) {
        mData.addAll(data);
    }


    public <T> T getItem(int position) {
        return (T) mData.get(position);
    }

    public ArrayList<T> getData() {
        return mData;
    }


    public void removeData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
    }


    //item 监听
    public OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mOnItemClickListener = li;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        mHeaderCount = mHeaderCount + 1;
        notifyItemInserted(0);
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //获取真正的位置
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - mHeaderCount;
    }

    //内容长度
    public int getContentItemCount() {
        return mData.size();
    }

    // 总长度  包含内容Item+header
    @Override
    public int getItemCount() {
        return mHeaderCount + CollectionUtils.getSize(mData);
    }

    //如果是多布局  重写此方法即可
    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount) {
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (mHeaderCount != 0 && viewType == TYPE_HEADER) {
            return new HeaderHolder(mHeaderView);
        }
        return mOnCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (isHeaderView(position)) {

        } else {
            final int pos = getRealPosition(viewHolder);
            final T data = mData.get(pos);
            mOnBindViewHolder(viewHolder, pos, data);
            if (mOnItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(pos, data);
                    }
                });
            }
        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }


    public abstract RecyclerView.ViewHolder mOnCreateViewHolder(ViewGroup parent, final int viewType);

    public abstract void mOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, T data);


    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }


}
