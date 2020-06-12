package com.module.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.basemodule.utils.FileUtils;
import com.basemodule.utils.LuBanUtils;
import com.basemodule.utils.getPhotoFromPhotoAlbum;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.module.ui.R;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by wangwei on 2019/4/12.
 */

public class UploadImageActivity extends AppCompatActivity {
    private Button choose_image, upload;
    private ImageView image;

    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    private static final int CODE_GALLERY_REQUEST = 0;// 相册选图标记
    private static final int CODE_CAMERA_REQUEST = 1;   // 相机拍照标记
    private static final int CODE_RESULT_REQUEST = 2;
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。（生成bitmap貌似有时要报错？可试下把大小弄小点）
    private static int output_X = 320;
    private static int output_Y = 320;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_upload);
        choose_image = findViewById(R.id.choose_image);
        upload = findViewById(R.id.upload);
        image = findViewById(R.id.image);
        setListener();

    }


    private void setListener() {
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(UploadImageActivity.this)
                        .load("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=Glidle%20DiskCacheStrategy&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=4017870632,1412198558&os=1652205624,2282656425&simid=4017870632,1412198558&pn=11&rn=1&di=6960&ln=163&fr=&fmq=1558072106902_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=11&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fs16.sinaimg.cn%2Fmw690%2F007IqMWxzy7t5AY2QHB4f%26690&rpstart=0&rpnum=0&adpicid=0&force=undefined")
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false)
                        .dontAnimate()
                        .into(image);
//                if (PermissionUtils.checkPermission(UploadImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    goPhotoAlbum();
//                } else {
//                    PermissionUtils.requestPermission(UploadImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, 100);
//                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        //设置文件类型  如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        intent.setType("image/*");
        startActivityForResult(intent, CODE_GALLERY_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_GALLERY_REQUEST:
                    if (data != null) {
                        String photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
                        File file = new File(photoPath);
                        Glide.with(UploadImageActivity.this).load(photoPath).into(image);
                        Log.w("TAG", "-压缩前-->" + FileUtils.getInstance().computeSize(file)[0] + "--->" + FileUtils.getInstance().computeSize(file)[1]);
                        Log.w("TAG", "压缩前-----" + file.length() / 1024 + "KB");
                        comImage(photoPath);
                    }

                    break;
            }
        }
    }


    /**
     * 压缩文件
     */
    private void comImage(File file) {
        LuBanUtils.LuBanCompressImage(file, new LuBanUtils.CompressListener() {
            @Override
            public void onSuccess(File file) {
                Log.w("TAG", "onSuccess--" + Thread.currentThread());
                Log.w("TAG", "-压缩后-->" + FileUtils.getInstance().computeSize(file)[0] + "--->" + FileUtils.getInstance().computeSize(file)[1]);
                Log.w("TAG", "---压缩后---->" + file.length() / 1024 + "KB");
            }

            @Override
            public void onError() {

            }
        });
    }

    /**
     * 压缩图片
     */
    private void comImage(String file) {

    }
}

