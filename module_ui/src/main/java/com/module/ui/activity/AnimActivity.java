package com.module.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.module.ui.R;
import com.module.ui.widget.favor.FavorWidget3;

import androidx.appcompat.app.AppCompatActivity;
import me.yifeiyuan.library.PeriscopeLayout;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/5/19.
 */
public class AnimActivity extends AppCompatActivity {
    FavorWidget3 favorWidget;

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

        favorWidget = findViewById(R.id.favor_widget);


        favorWidget.setFavorSender(new FavorWidget3.FavorSender() {

            @Override
            public void onSendFavor(int count) {
                Log.w("TAG", "----onSendFavor--");
            }
        });
        // favorWidget.showFlyingAnim();
    }


    @Override
    protected void onResume() {
        super.onResume();
        favorWidget.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        favorWidget.pause();
    }
}

