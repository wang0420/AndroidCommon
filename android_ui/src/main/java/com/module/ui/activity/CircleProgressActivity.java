package com.module.ui.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.module.ui.R;
import com.module.ui.widget.CircleProgressView;
import com.module.ui.widget.FuChenImageView;


/**
 * Created by wangwei on 2018/9/27.
 */

public class CircleProgressActivity extends AppCompatActivity {

    private CircleProgressView mCircleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_progress_layout);

        mCircleProgressView = (CircleProgressView) findViewById(R.id.circle_view);

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
            }
        });

        FuChenImageView fuChenImageView = findViewById(R.id.img);
        fuChenImageView.setMoreNum(5);


    }
}
