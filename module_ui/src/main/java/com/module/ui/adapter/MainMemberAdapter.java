package com.module.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.module.ui.R;
import com.module.ui.bean.MemberBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by wangwei on 2018/3/16.
 */

public class MainMemberAdapter extends RecyclerView.Adapter<MainMemberAdapter.ViewHolder> {
    protected Context mContext;
    protected LayoutInflater mInflater;
    private List<MemberBean> mData = new ArrayList<>();

    public MainMemberAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void addItem(MemberBean item) {
        mData.add(item);
    }

    public void addAll(List<MemberBean> data) {
        mData.addAll(data);
    }

    public void clear() {
        mData.clear();
    }


    @Override
    public MainMemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(final MainMemberAdapter.ViewHolder holder, final int position) {

        holder.tvName.setText(this.mData.get(position).getName());

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mData.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTag;
        TextView tvName;
        View content;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.tag);
            tvName = itemView.findViewById(R.id.name);

        }
    }

    /**
     * 根据当前位置获取分类的首字母的char  ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
