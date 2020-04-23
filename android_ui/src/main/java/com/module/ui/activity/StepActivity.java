package com.module.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.module.ui.R;
import com.module.ui.util.DisplayUtil;
import com.module.ui.widget.StepInfoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 2019/6/19.
 */

public class StepActivity extends AppCompatActivity {
    private StepInfoView stepInfoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_step);


        stepInfoView = findViewById(R.id.stepInfoView);
        List<String> mDatas = new ArrayList<>();
        mDatas.add("基本信息");
        mDatas.add("择偶条件");
        mDatas.add("签字确认");
        stepInfoView.setStepData(mDatas);
        stepInfoView.setStepPostion(0);


    }
}
