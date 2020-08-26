package com.module.ui.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.common.router.CommonProviderPath;
import com.android.common.router.RouterManager;
import com.android.common.router.provider.IRouterProvider;
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


            }
        });

        FuChenImageView fuChenImageView = findViewById(R.id.img);
        fuChenImageView.setMoreNum(5);
      /*  IAccountProvider provider = (IAccountProvider) ARouter.getInstance()
                .build(CommonProviderPath.ACCOUNT_PROVIDER)
                .navigation();
        Toast.makeText(this, "" + provider.getUserId(), Toast.LENGTH_LONG).show();
*/

        IRouterProvider provider = RouterManager.getProvider(CommonProviderPath.ROUTER_PROVIDER);

        /*IRouterProvider provider1 = (IRouterProvider) ARouter.getInstance()
                .build(CommonProviderPath.ROUTER_PROVIDER)
                .navigation();*/
        provider.build().type(2).router(CircleProgressActivity.this);


    }

    @Override
    public void initView() {
        String ss = "%s 申请与 %s 牵线，请及时引导";
        String aa = "十年之前我不认识你";
        String formatted = String.format(ss, aa, aa);
        Log.w("TAG", "=====" + formatted);

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
