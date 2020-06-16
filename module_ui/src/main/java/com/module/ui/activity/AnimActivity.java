package com.module.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.module.ui.R;
import com.module.ui.widget.favor.FavorWidget2;

import androidx.appcompat.app.AppCompatActivity;
import me.yifeiyuan.library.PeriscopeLayout;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/5/19.
 */
public class AnimActivity extends AppCompatActivity {
    FavorWidget2 favorWidget;

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


        // 根据主题获取点赞图片
        Bitmap[] bitmaps = null;
        if (bitmaps == null) {
            Bitmap bitmap1 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.love_zone_pic_sweetness_love1, null);
            Bitmap bitmap2 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.love_zone_pic_sweetness_love2, null);
            Bitmap bitmap3 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.love_zone_pic_sweetness_love3, null);
            Bitmap bitmap4 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.love_zone_pic_sweetness_love4, null);
            Bitmap[] remoteIcons = {bitmap1, bitmap2, bitmap3, bitmap4};

        }
        favorWidget.setBitmaps(bitmaps, false);
        favorWidget.setFavorSender(new FavorWidget2.FavorSender() {

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

    private void initFavorWidget2(FavorWidget2 widget) {


    }

}

