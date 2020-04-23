package com.module.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.module.ui.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by afanbaby on 2017/9/19.
 */

public class TwoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button renderScriprtBtn, glideBtn;
    private ImageView imageView;
    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mohutwo);

        renderScriprtBtn = (Button) findViewById(R.id.renderScriprtBtn);
        glideBtn = (Button) findViewById(R.id.glideBtn);
        imageView = (ImageView) findViewById(R.id.image2);
        seekBar = (SeekBar) findViewById(R.id.seekBar2);
        textView = (TextView) findViewById(R.id.text2);
        renderScriprtBtn.setOnClickListener(this);
        glideBtn.setOnClickListener(this);

        seekBar.setMax(25);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Bitmap mBitmap1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.love)).getBitmap();
                Bitmap bitmap = blurBitmap(TwoActivity.this, mBitmap1, i);
                imageView.setImageBitmap(bitmap);
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
        if (i == R.id.renderScriprtBtn) {
            Toast.makeText(this, "RenderScriprt实现高斯模糊", Toast.LENGTH_SHORT).show();
            Bitmap mBitmap1 = ((BitmapDrawable) getResources().getDrawable(R.drawable.love)).getBitmap();
            Bitmap bitmap = blurBitmap(this, mBitmap1, 15);
            imageView.setImageBitmap(bitmap);

        } else if (i == R.id.glideBtn) {
            Toast.makeText(this, "Glide实现高斯模糊", Toast.LENGTH_SHORT).show();
            Glide.with(this).load(R.drawable.love)
                    //设置高斯模糊
                    // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                    .apply(bitmapTransform(new BlurTransformation(this, 25)))
                    .into(imageView);


        }
    }

    /**
     * 获取模糊的图片
     *
     * @param context 上下文对象
     * @param bitmap  传入的bitmap图片
     * @param radius  模糊度（Radius最大只能设置25.f）
     * @return
     */
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, int radius) {
        //用需要创建高斯模糊bitmap创建一个空的bitmap
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // 初始化Renderscript，该类提供了RenderScript context，创建其他RS类之前必须先创建这个类，其控制RenderScript的初始化，资源管理及释放
        RenderScript rs = RenderScript.create(context);
        // 创建高斯模糊对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 创建Allocations，此类是将数据传递给RenderScript内核的主要方 法，并制定一个后备类型存储给定类型
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        //设定模糊度(注：Radius最大只能设置25.f)
        blurScript.setRadius(radius);
        // Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        // Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);
        // recycle the original bitmap
        // bitmap.recycle();
        // After finishing everything, we destroy the Renderscript.
        rs.destroy();
        return outBitmap;
    }
}
