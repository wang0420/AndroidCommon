package com.module.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.module.ui.R;
import com.module.ui.widget.HorizontalScrollUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 2019/5/20.
 */

public class HorizontalScrollViewActivity extends AppCompatActivity {
    HorizontalScrollUI scroll_view;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hor_scroll_layout);
        scroll_view = findViewById(R.id.scroll_view);
        for (int i = 0; i < 20; i++) {
            mData.add("标签" + i);
        }
        scroll_view.setData(mData);
        scroll_view.setTagClickLitener(new HorizontalScrollUI.OnTagClickLitener() {
            @Override
            public void onTagClick(int index) {
                Log.w("TAG", "index---" + index);

            }
        });


    }

}
