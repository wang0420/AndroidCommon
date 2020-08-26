package com.module.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.common.router.ActivityPath;
import com.module.ui.R;

import net.qiujuer.genius.blur.StackBlur;

/**
 * Created by afanbaby on 2017/9/19.
 */
@Route(path = ActivityPath.VERSION_CENTER_ACTIVITY)
public class MoHuImageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button javaBtn, bjnBtn, pjnBtn, otherBtn;
    private ImageView imageView;
    private int radius = 0;
    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        javaBtn = (Button) findViewById(R.id.javaBtn);
        bjnBtn = (Button) findViewById(R.id.bjnBtn);
        pjnBtn = (Button) findViewById(R.id.pjnBtn);
        otherBtn = (Button) findViewById(R.id.otherBtn);
        imageView = (ImageView) findViewById(R.id.image);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.text);
        javaBtn.setOnClickListener(this);
        bjnBtn.setOnClickListener(this);
        pjnBtn.setOnClickListener(this);
        otherBtn.setOnClickListener(this);

        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Bitmap mBitmap1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.love)).getBitmap();
                // Java
                Bitmap newBitmap1 = StackBlur.blur(mBitmap1, (int) i, false);
                imageView.setImageBitmap(newBitmap1);

                textView.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.javaBtn) {
            Bitmap mBitmap1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.love)).getBitmap();
            // Java
            Bitmap newBitmap1 = StackBlur.blur(mBitmap1, 20, false);
            imageView.setImageBitmap(newBitmap1);
            Toast.makeText(this, "使用java实现", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.bjnBtn) {
            Bitmap mBitmap2 = ((BitmapDrawable) getResources().getDrawable(R.drawable.love)).getBitmap();
            // Bitmap JNI Native
            Bitmap newBitmap2 = StackBlur.blurNatively(mBitmap2, 20, false);
            imageView.setImageBitmap(newBitmap2);
            Toast.makeText(this, "使用Bitmap JNI Native实现", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.pjnBtn) {
            Bitmap mBitmap3 = ((BitmapDrawable) getResources().getDrawable(R.drawable.love)).getBitmap();
            // Pixels JNI Native
            Bitmap newBitmap3 = StackBlur.blurNativelyPixels(mBitmap3, 20, false);
            imageView.setImageBitmap(newBitmap3);
            Toast.makeText(this, "使用Pixels JNI Native实现", Toast.LENGTH_SHORT).show();

        } else if (i == R.id.otherBtn) {
            startActivity(new Intent(this, TwoActivity.class));

        }
    }

}
