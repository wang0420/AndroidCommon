package com.module.ui.activity;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.module.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 2018/9/27.
 */

public class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.MyViewHolder> {
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private int clickPosition = 0;

    public ArrayList<String> mDatas = new ArrayList<>();

    public void addItem(String item) {
        mDatas.add(item);
    }

    public void addAll(List<String> data) {
        mDatas.addAll(data);
    }


    public interface OnItemClickLitener {
        void onItemClick(int index);
    }

    private ScrollAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(ScrollAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ScrollAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }


    @Override
    public ScrollAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View inflate = mLayoutInflater.inflate(R.layout.scroll_item, parent, false);
        ScrollAdapter.MyViewHolder masterViewHolder = new ScrollAdapter.MyViewHolder(inflate);
        return masterViewHolder;
    }

    @Override
    public void onBindViewHolder(final ScrollAdapter.MyViewHolder holder, final int position) {
        final String oj = mDatas.get(position);
        upDateHolderView(holder, oj, position);
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPosition = position;
                    mOnItemClickLitener.onItemClick(position);
                    notifyDataSetChanged();
                }
            });
        }

    }

    private void upDateHolderView(ScrollAdapter.MyViewHolder holder, String oj, int position) {
        holder.item_tv.setText(oj);
        if (position == clickPosition) {
            holder.item_tv.setSelected(true);
        } else {
            holder.item_tv.setSelected(false);


        }
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



