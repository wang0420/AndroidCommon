package com.module.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.module.ui.R;
import com.module.ui.widget.photoview.OnPhotoTapListener;
import com.module.ui.widget.photoview.PhotoView;


/**
 * <p>
 * viewpager中图片的fragment
 */
public class PhotoGallerylFragment extends Fragment {
    private String mImageUrl;
    private PhotoView mImageView;
    private Bundle mBundle;
    private ProgressBar load;


    public static PhotoGallerylFragment newInstance(String imageUrl) {
        PhotoGallerylFragment contentFragment = new PhotoGallerylFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("url", imageUrl);
        contentFragment.setArguments(mBundle);
        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        if (mBundle != null) {
            mImageUrl = mBundle.getString("url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_pager, container, false);
        mImageView = v.findViewById(R.id.image);
        load = v.findViewById(R.id.loading);
        mImageView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });

        loadPhoto();
        return v;
    }

    public void loadPhoto() {

        Glide.with(getActivity())
                .load(mImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mImageView.setImageDrawable(resource);
                        //在这里添加一些图片加载完成的操作
                        load.setVisibility(View.GONE);
                    }
                });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mImageView = null;
    }

}