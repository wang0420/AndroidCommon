package com.module.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.module.ui.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangwei on 2018/9/27.
 */

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.MyViewHolder> {
    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public ArrayList<String> mDatas = new ArrayList<>();

    public void addItem(String item) {
        mDatas.add(item);
    }

    public void addAll(List<String> data) {
        mDatas.addAll(data);
    }

    public void clear() {
        mDatas.clear();
    }

    public interface OnItemClickLitener {
        void onItemClick(int index, String keyWord);
    }

    private SearchItemAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(SearchItemAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public SearchItemAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }


    @Override
    public SearchItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View inflate = mLayoutInflater.inflate(R.layout.main_item, parent, false);
        SearchItemAdapter.MyViewHolder masterViewHolder = new SearchItemAdapter.MyViewHolder(inflate);
        return masterViewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchItemAdapter.MyViewHolder holder, final int position) {
        final String oj = mDatas.get(position);
        upDateHolderView(holder, oj, position);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(position, oj);
                }
            });
        }
    }

    private void upDateHolderView(SearchItemAdapter.MyViewHolder holder, String oj, int position) {
        holder.item_tv.setText(oj);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        public TextView item_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            item_tv = itemView.findViewById(R.id.item_tv);
        }

    }

}



