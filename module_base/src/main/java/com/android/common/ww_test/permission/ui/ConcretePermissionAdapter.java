package com.android.common.ww_test.permission.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * User: newSalton@outlook.com
 * Date: 2019/3/21 16:57
 * ModifyTime: 16:57
 * Description:
 */
public class ConcretePermissionAdapter extends RecyclerView.Adapter<ConcretePermissionAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    public ArrayList<PermissionBean> mData = new ArrayList<>();

    public ConcretePermissionAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    public void addItem(PermissionBean data) {
        mData.add(data);
    }

    public void addAll(List<PermissionBean> data) {
        mData.clear();
        mData.addAll(data);
    }

    public void clear() {
        mData.clear();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View item, int position);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.adapter_item_concrete_permission, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        PermissionBean bean = mData.get(position);
        holder.ivPermissionTypeIcon.setImageResource(bean.iconRes);
        holder.tvTitle.setText(bean.name);
        holder.tvDesc.setText(bean.desc);


    }

    @Override
    public int getItemCount() {
        return mData.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPermissionTypeIcon;
        TextView tvTitle;
        TextView tvDesc;


        public ViewHolder(View itemView) {
            super(itemView);
            ivPermissionTypeIcon = itemView.findViewById(R.id.ivPermissionTypeIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);

        }
    }


}