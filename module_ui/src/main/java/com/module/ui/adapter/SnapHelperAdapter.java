package com.module.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.module.ui.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by chenzhimao on 17-7-6.
 */

public class SnapHelperAdapter extends RecyclerView.Adapter<SnapHelperAdapter.GalleryViewHoler> {
    LayoutInflater mInflater;
    ArrayList<String> mData;
    int[] imgs = new int[]{R.mipmap.jdzz, R.mipmap.ccdzz, R.mipmap.dfh, R.mipmap.dlzs, R.mipmap.sgkptt, R.mipmap.ttxss, R.mipmap.zmq, R.mipmap.zzhx};

    int type;

    public SnapHelperAdapter(Context context, ArrayList<String> data, int type) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.type = type;

    }

    @Override
    public GalleryViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(type == 1 ? R.layout.gallery_item_layout : R.layout.gallery_item_layout1, parent, false);
// android:layout_width="300"固定宽度  可实现ViewPager  两边有突出效果

        return new GalleryViewHoler(view);
    }

    @Override
    public void onBindViewHolder(final GalleryViewHoler holder, int position) {
        holder.mImageView.setImageResource(imgs[position % 8]);
        holder.mTextView.setText(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class GalleryViewHoler extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public GalleryViewHoler(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.image);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
