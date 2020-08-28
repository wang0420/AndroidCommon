package com.android.newcommon.floatview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.common.R;
import com.android.newcommon.utils.DisplayUtil;

/**
 *
 */

public class FloatPageView extends BaseFloatPage implements TouchProxy.OnTouchEventListener {
    public static final int UPDATE_DATA_WHAT = 0x123;
    private WindowManager mWindowManager;
    private TouchProxy mTouchProxy = new TouchProxy(this);

    TextView mMemoryTxt;
    TextView mDownNetworkTxt;
    TextView mCpuTxt;
    TextView mUpNetworkTxt;
    TextView mFpsTxt;

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

    }

    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_perform_data, null);
    }

    @Override
    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.x = DisplayUtil.dpToPx(getContext(), 30);
        params.y = DisplayUtil.dpToPx(getContext(), 30);
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        initView();
    }

    private void initView() {
        getRootView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mTouchProxy.onTouchEvent(v, event);
            }
        });
        mMemoryTxt = findViewById(R.id.memory_txt);
        mDownNetworkTxt = findViewById(R.id.down_network_txt);
        mCpuTxt = findViewById(R.id.cpu_txt);
        mUpNetworkTxt = findViewById(R.id.up_network_txt);
        mFpsTxt = findViewById(R.id.fps_txt);

        //mHandler.sendEmptyMessage(UPDATE_DATA_WHAT);
    }


    @Override
    public void onMove(int x, int y, int dx, int dy) {
        getLayoutParams().x += dx;
        getLayoutParams().y += dy;
        mWindowManager.updateViewLayout(getRootView(), getLayoutParams());
    }

    @Override
    public void onUp(int x, int y) {

    }

    @Override
    public void onDown(int x, int y) {

    }

    @Override
    public void onEnterForeground() {
        super.onEnterForeground();
        getRootView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onEnterBackground() {
        super.onEnterBackground();
        getRootView().setVisibility(View.GONE);
      //  TimeCounterManager.get().onEnterBackground();
    }
}