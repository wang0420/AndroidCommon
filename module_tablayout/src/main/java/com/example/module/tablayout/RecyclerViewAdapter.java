package com.example.module.tablayout;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.module.tablayout.other.OthetMainActivity;
import com.example.module.tablayout.yangfan.modifytablayout.DiyInterActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    public RecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
        final View view = holder.mView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (0 == position) {
                    Intent mIntent = new Intent(mContext, CollapsingToolbarActivity.class);
                    mContext.startActivity(mIntent);
                } else if (1 == position) {
                    Intent mIntent = new Intent(mContext, ZheDieImageActivity.class);
                    mContext.startActivity(mIntent);
                } else if (2 == position) {
                    Intent mIntent = new Intent(mContext, ThreeActivity.class);
                    mContext.startActivity(mIntent);
                } else if (3 == position) {
                    Intent mIntent = new Intent(mContext, TabLayoutActivity.class);
                    mContext.startActivity(mIntent);
                } else if (4 == position) {
                    Intent mIntent = new Intent(mContext, TabLayoutZHeDieActivity.class);
                    mContext.startActivity(mIntent);
                } else if (5 == position) {
                    Intent mIntent = new Intent(mContext, OthetMainActivity.class);
                    mContext.startActivity(mIntent);
                } else if (6 == position) {
                    Intent mIntent = new Intent(mContext, DiyTabActivity.class);
                    mContext.startActivity(mIntent);
                }else if (7 == position) {
                    Intent mIntent = new Intent(mContext, DiyInterActivity.class);
                    mContext.startActivity(mIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }
}
