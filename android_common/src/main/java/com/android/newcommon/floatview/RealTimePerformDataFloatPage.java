package com.android.newcommon.floatview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.common.R;
import com.android.newcommon.monitor.PerformanceDataManager;
import com.android.newcommon.utils.DisplayUtil;

import java.text.DecimalFormat;


/**
 *
 */

public class RealTimePerformDataFloatPage extends BaseFloatPage implements TouchProxy.OnTouchEventListener {
    public static final int UPDATE_DATA_WHAT = 0x123;
    private WindowManager mWindowManager;
    private TouchProxy mTouchProxy = new TouchProxy(this);

    TextView mMemoryTxt;
    TextView mDownNetworkTxt;
    TextView mCpuTxt;
    TextView mUpNetworkTxt;
    TextView mFpsTxt;
    ImageView delete;
    private Handler mHandler;

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                showInfo();
                mHandler.sendEmptyMessageDelayed(UPDATE_DATA_WHAT, 1000);
            }
        };
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
        delete = findViewById(R.id.delete);
        mMemoryTxt = findViewById(R.id.memory_txt);
        mDownNetworkTxt = findViewById(R.id.down_network_txt);
        mCpuTxt = findViewById(R.id.cpu_txt);
        mUpNetworkTxt = findViewById(R.id.up_network_txt);
        mFpsTxt = findViewById(R.id.fps_txt);

        mHandler.sendEmptyMessage(UPDATE_DATA_WHAT);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformanceDataManager.getInstance().stopUploadMonitorData();
                FloatPageManager.getInstance().removeAll(RealTimePerformDataFloatPage.class);
            }
        });
    }

    private static final DecimalFormat decimal = new DecimalFormat("#.00");

    @SuppressLint("DefaultLocale")
    private void showInfo() {
        PerformanceDataManager manager = PerformanceDataManager.getInstance();

        mMemoryTxt.setText(getString(R.string.dk_frameinfo_ram) + ":" + decimal.format(manager.getLastMemoryInfo())
                + "\n最大内存: " + decimal.format(manager.getMaxMemory()));

        mCpuTxt.setVisibility(View.VISIBLE);
        mCpuTxt.setText(getString(R.string.dk_frameinfo_cpu) + ":" + manager.getLastCpuRate());


        mFpsTxt.setVisibility(View.VISIBLE);
        mFpsTxt.setText(getString(R.string.dk_frameinfo_fps) + ":" + decimal.format(manager.getLastFrameRate()));


    }

    public static String getFlowTxt(long flowBytes) {
        String upFlowTxt = flowBytes + "B";
        if (1073741824 < flowBytes) {
            upFlowTxt = flowBytes / 1073741824 + "GB";
        } else if (1048576 < flowBytes) {
            upFlowTxt = flowBytes / 1048576 + "MB";
        } else if (1024 < flowBytes) {
            upFlowTxt = flowBytes / 1024 + "KB";
        }

        return upFlowTxt;
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