package com.moudlea.rxjava;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
