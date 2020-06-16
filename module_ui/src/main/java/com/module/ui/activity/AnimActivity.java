package com.module.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.module.ui.R;

import androidx.appcompat.app.AppCompatActivity;
import me.yifeiyuan.library.PeriscopeLayout;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/5/19.
 */
public class AnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_layout);
        PeriscopeLayout widget = findViewById(R.id.periscope);
        widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                widget.addHeart();
            }
        });
    }


}

