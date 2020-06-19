package com.moudlea.rxjava;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.common.utils.DisplayUtil;
import com.moudlea.R;
import com.moudlea.rxjava.op.OperateAsyncSubject;
import com.moudlea.rxjava.op.OperateBehaviorSubject;
import com.moudlea.rxjava.op.OperateBuffer;
import com.moudlea.rxjava.op.OperateCompletable;
import com.moudlea.rxjava.op.OperateConcat;
import com.moudlea.rxjava.op.OperateDebounce;
import com.moudlea.rxjava.op.OperateDefer;
import com.moudlea.rxjava.op.OperateDelay;
import com.moudlea.rxjava.op.OperateDisposable;
import com.moudlea.rxjava.op.OperateDistinct;
import com.moudlea.rxjava.op.OperateFilter;
import com.moudlea.rxjava.op.OperateFlowable;
import com.moudlea.rxjava.op.OperateInterval;
import com.moudlea.rxjava.op.OperateLast;
import com.moudlea.rxjava.op.OperateMap;
import com.moudlea.rxjava.op.OperateMerge;
import com.moudlea.rxjava.op.OperatePublishSubject;
import com.moudlea.rxjava.op.OperateReplay;
import com.moudlea.rxjava.op.OperateReplaySubject;
import com.moudlea.rxjava.op.OperateScan;
import com.moudlea.rxjava.op.OperateSkip;
import com.moudlea.rxjava.op.OperateSwitchMap;
import com.moudlea.rxjava.op.OperateTake;
import com.moudlea.rxjava.op.OperateThrottleFirst;
import com.moudlea.rxjava.op.OperateThrottleLast;
import com.moudlea.rxjava.op.OperateTimer;
import com.moudlea.rxjava.op.OperateWindow;
import com.moudlea.rxjava.op.OperateZip;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * RxJava 常用操作符
 */
public class RxActivity extends AppCompatActivity {
    private EditText etInput;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        etInput = findViewById(R.id.et_rx);
        RxjavaUtil.delay();


        ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
//            FrameLayout contentView = decorView.findViewById(android.R.id.content);
        ImageView imageView = new ImageView(this);

        imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_launcher));

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DisplayUtil.dip2px(this, 36),
                DisplayUtil.getTitlebarHeight(this) + DisplayUtil.dip2px(this, 20),
                DisplayUtil.dip2px(this, 36),
                0);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(layoutParams);
        decorView.addView(imageView);

        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (imageView != null) {
                            decorView.removeView(imageView);
                        }
                    }
                });


    }

    public void doSome(View view) {
        String s = etInput.getText().toString();
        if (s.isEmpty()) {
            Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
            return;
        }
        index = Integer.parseInt(s);
        switch (index) {
            case 0:
                OperateAsyncSubject.doSome();
                break;
            case 1:
                OperateBehaviorSubject.doSome();
                break;
            case 2:
                OperateBuffer.doSome();
                break;
            case 3:
                OperateCompletable.doSome();
                break;
            case 4:
                OperateConcat.doSome();
                break;
            case 5:
                OperateDebounce.doSome();
                break;
            case 6:
                OperateDefer.doSome();
                break;
            case 7:
                OperateDelay.doSome();
                break;
            case 8:
                OperateDisposable.doSome();
                break;
            case 9:
                OperateDistinct.doSome();
                break;
            case 10:
                OperateFilter.doSome();
                break;
            case 11:
                OperateFlowable.doSome();
                break;
            case 12:
                OperateInterval.doSome();
                break;
            case 13:
                OperateLast.doSome();
                break;
            case 14:
                OperateMap.doSome();
                break;
            case 15:
                OperateMerge.doSome();
                break;
            case 16:
                OperatePublishSubject.doSome();
                break;
            case 17:
                OperateReplay.doSome();
                break;
            case 18:
                OperateReplaySubject.doSome();
                break;
            case 19:
                OperateScan.doSome();
                break;
            case 20:
                OperateSkip.doSome();
                break;
            case 21:
                OperateSwitchMap.doSome();
                break;
            case 22:
                OperateTake.doSome();
                break;
            case 23:
                OperateThrottleFirst.doSome();
                break;
            case 24:
                OperateThrottleLast.doSome();
                break;
            case 25:
                OperateTimer.doSome();
                break;
            case 26:
                OperateWindow.doSome();
                break;
            case 27:
                OperateZip.doSome();
                break;

            default:
                Toast.makeText(this, "请输入正确的数字", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
