package com.basemodule.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.basemodule.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;


/**
 * Glide工具类
 * Created by VegC on 2017/2/18.
 */
public class GlideUtils {
    public static final int placeholderId = R.drawable.default_pic_loading;
    public static final int errorId = R.drawable.default_pic_loading;

    public static void loadImage(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        loadImage(context, imageView, url, placeholderId, errorId, null);
    }


    public static void loadImage(Context context, final ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        loadImage(context, imageView, resId, placeholderId, errorId, null);
    }

    /**
     * https://muyangmin.github.io/glide-docs-cn/doc/placeholders.html
     *
     * @param context
     * @param imageView
     * @param resId
     * @param placeholderId
     * @param errorId
     * @param tr            图片变换相关的属性  https://blog.csdn.net/u010356768/article/details/78455117
     */
    public static void loadImage(Context context, final ImageView imageView, int resId, int placeholderId, int errorId, Transformation<Bitmap> tr) {

        if (tr == null) {
            tr = new CenterCrop();
        }
        Glide.with(context)
                .load(resId)
                .transform(tr)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .placeholder(placeholderId)
                .error(errorId)
                .into(imageView);
    }

    public static void loadImage(Context context, final ImageView imageView, String resId, int placeholderId, int errorId, Transformation<Bitmap> tr) {
        if (tr == null) {
            tr = new CenterCrop();
        }
        Glide.with(context)
                .load(resId)
                .transform(tr)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .placeholder(placeholderId)
                .error(errorId)
                .into(imageView);

    }

    public static void loadImage(Context context, final ImageView imageView, String resId, Transformation<Bitmap> tr) {
        if (tr == null) {
            tr = new CenterCrop();
        }
        Glide.with(context)
                .load(resId)
                .transform(tr)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .placeholder(placeholderId)
                .error(errorId)
                .into(imageView);

    }

}