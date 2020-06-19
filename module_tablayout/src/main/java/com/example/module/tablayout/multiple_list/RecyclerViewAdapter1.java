package com.example.module.tablayout.multiple_list;

/**
 * Created by wangwei on 2020/5/12.
 */

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.common.utils.ColorUtils;
import com.example.module.tablayout.R;
import com.example.module.tablayout.R2;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/14
 */

public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.RecyclerViewHolder> implements ItemTouchStatus {

    private List<String> mDataList;

    public RecyclerViewAdapter1(List<String> dataList) {
        mDataList = dataList;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        holder.mTextView.setText(mDataList.get(position));
        Log.w("TAG", "position---" + ColorUtils.generateRandomColor());
        Log.w("TAG", "position---" + Color.parseColor(ColorUtils.generateRandomColor()));


        holder.itemView.setBackgroundColor(Color.parseColor(ColorUtils.generateRandomColor()));
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("TAG", "position---" + position);
                Log.w("TAG", "position---" + mDataList.toString());


            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //是否开启item长按拖拽功能
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        //notifyDataSetChanged();
        return true;
    }

    @Override
    public boolean onItemRemove(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
        return true;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.textView)
        TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
