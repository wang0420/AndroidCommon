package com.android.newcommon.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.common.R;
import com.android.common.utils.SoftInputManager;

/**
 * Created by wangwei on 2019/3/11.
 * App通用Title
 */

public class BaseTitleBar extends RelativeLayout {
    private ImageView leftBtn;
    private TextView rightBtn;
    private TextView titleTv;
    private RelativeLayout titleLayout;
    //有的页面需要自己处理返回按钮
    private BackListener mBackListener;
    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mBackListener != null) {
                mBackListener.onBackClick();
            } else {
                Activity activity = getActivityFromView(view);
                if (null != activity) {
                    SoftInputManager.hideSoftInput(activity);
                    activity.finish();
                }
            }
        }
    };


    public BaseTitleBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BaseTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(final Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this, true);
        leftBtn = findViewById(R.id.iv_left);
        titleTv = findViewById(R.id.header_title);
        rightBtn = findViewById(R.id.tv_right);
        titleLayout = findViewById(R.id.title_parent);
        leftBtn.setOnClickListener(listener);

        try {
            if (attrs != null) {
                TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBar);
                boolean isShowBack = tArray.getBoolean(R.styleable.BaseTitleBar_isShowBack, false);
                String titleText = tArray.getString(R.styleable.BaseTitleBar_titleText);
                String rightText = tArray.getString(R.styleable.BaseTitleBar_rightText);
                tArray.recycle();
                if (isShowBack) {
                    leftBtn.setVisibility(View.VISIBLE);
                } else {
                    leftBtn.setVisibility(View.GONE);
                }
                titleTv.setText(titleText);
                rightBtn.setText(rightText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 题目颜色
     */
    public void setTitleBackground(int color) {
        titleLayout.setBackgroundColor(color);
    }

    /**
     * 左边按钮
     */
    public ImageView getLeftBtn() {
        leftBtn.setVisibility(View.VISIBLE);
        return leftBtn;
    }

    /**
     * 右边按钮
     */
    public TextView getRightBtn() {
        rightBtn.setVisibility(View.VISIBLE);
        return rightBtn;
    }

    /**
     * 中间title
     */
    public TextView getTitleTv() {
        return titleTv;
    }

    /**
     * 设置中间title文字
     */
    public void setTitleText(CharSequence text) {
        titleTv.setText(text);
    }

    /**
     * 设置中间title文字
     */
    public void setTitleText(int resid) {
        titleTv.setText(resid);
    }

    /**
     * 设置中间title文字颜色
     */
    public void setTitleTextColor(int color) {
        titleTv.setTextColor(color);
    }




    /**
     * 设置左边按钮点击事件
     */
    public void setBackListener(BackListener listener) {
        mBackListener = listener;
    }

    public interface BackListener {
        void onBackClick();
    }

    /**
     * try get host activity from view.
     * views hosted on floating window like dialog and toast will sure return null.
     *
     * @return host activity; or null if not available
     */
    public static Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
