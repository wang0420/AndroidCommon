package com.android.newcommon.monitor.crash;

import android.util.Log;
import android.widget.TextView;

import com.android.common.R;
import com.android.newcommon.base.BaseTitleActivity;
import com.android.newcommon.monitor.util.FileManager;
import com.android.newcommon.monitor.util.ServiceUtils;

/**
 * @author wangwei
 * @date 2020/8/28.
 */
public class CrashPanelAty extends BaseTitleActivity {
    public TextView tvCrashInfo;

    @Override
    public void initView() {
        tvCrashInfo = findViewById(R.id.tvCrashInfo);
    }

    @Override
    public void initData() {
        String fileName = getIntent().getStringExtra(ServiceUtils.FLAG_INFO);
        Log.w("TAG", "fileName--->" + fileName);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String info = FileManager.readFile(fileName, "UTF-8").toString();
                Log.w("TAG", "info--->" + info);

                tvCrashInfo.post(new Runnable() {
                    @Override
                    public void run() {
                        tvCrashInfo.setText(info);
                    }
                });

            }
        }).start();

    }


    @Override
    public int layoutResID() {
        return R.layout.qa_aty_crash_panel;
    }
}
