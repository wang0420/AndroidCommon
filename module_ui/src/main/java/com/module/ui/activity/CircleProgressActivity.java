package com.module.ui.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.android.newcommon.base.BaseTitleActivity;
import com.android.newcommon.widget.BaseTitleBar;
import com.android.newcommon.widget.MultipleStatusView;
import com.module.ui.R;
import com.module.ui.R2;
import com.module.ui.widget.CircleProgressView;
import com.module.ui.widget.FuChenImageView;

import androidx.annotation.Nullable;
import butterknife.BindView;


/**
 * Created by wangwei on 2018/9/27.
 */

public class CircleProgressActivity extends BaseTitleActivity {

    @BindView(R2.id.circle_view)
    CircleProgressView mCircleProgressView;

    private MultipleStatusView multipleStatusView;

    @Override
    public int layoutResID() {
        return R.layout.circle_progress_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        multipleStatusView = findViewById(R.id.multiple_status_view);
       // mCircleProgressView = (CircleProgressView) findViewById(R.id.circle_view);
        //进度条从0到100
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (float) animation.getAnimatedValue();
                mCircleProgressView.setmCurrent((int) current);
            }
        });
        animator.start();

        mCircleProgressView.setOnLoadingCompleteListener(new CircleProgressView.OnLoadingCompleteListener() {
            @Override
            public void complete() {
                Toast.makeText(CircleProgressActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                //changePageState(PageState.ERROR);//切换成网络异常布局
                // multipleStatusView.showEmpty();
            }
        });

        FuChenImageView fuChenImageView = findViewById(R.id.img);
        fuChenImageView.setMoreNum(5);
    }

    @Override
    public void initView() {
        setTitleBarVisible(true);
        getBaseTitleBar().setBackListener(new BaseTitleBar.BackListener() {
            @Override
            public void onBackClick() {
                Toast.makeText(CircleProgressActivity.this, "ww", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void initData() {

    }


}
