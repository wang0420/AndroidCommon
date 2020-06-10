package com.module.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.basemodule.za_test.permission.PermissionUtil;
import com.basemodule.za_test.permission.RxPermissionUtil;
import com.basemodule.za_test.permission.Utils;
import com.module.ui.R;

import java.io.File;
import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    // 相机权限、多个权限
    private final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_premission);

    }

    String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_main_request_one_permission) {
            requestPermission();
        } else if (id == R.id.btn_main_request_permissions) {
            PermissionUtil.checkAndRequestMorePermissions(mContext, PERMISSIONS, new PermissionUtil.PermissonCallBack() {
                @Override
                public void onGranted() {

                }
            });


        } else if (id == R.id.btn_main_request_one_permission1) {
            if (PermissionUtil.hasPermission(this, PERMISSION_CALL_PHONE)) {
                Toast.makeText(this, "已经有了权限", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission1();
            }

        } else if (id == R.id.btn_main_request_permissions1) {
            requestMorePermission();
        }
    }

    // 自定义申请一个权限
    private void requestPermission1() {
        PermissionUtil.requestSinglePermission(mContext, R.string.permission_des_storage, PERMISSION_CALL_PHONE, new PermissionUtil.PermissonCallBack() {
            @Override
            public void onDenied() {
                Log.w("TAG", "onDenied");

            }

            @Override
            public void onGranted() {
                Log.w("TAG", "onGranted");

            }
        });


    }

    // 普通申请一个权限组
    private void requestPermission() {
        PermissionUtil.requestCameraPermission(mContext, R.string.permission_des_storage, new PermissionUtil.PermissonCallBack() {
            @Override
            public void onDenied() {

            }

            @Override
            public void onGranted() {

            }
        });

    }


    // 自定义申请多个个权限
    private void requestMorePermission() {
        String[] PERMISSIONS = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR};
        PermissionUtil.requestPermission(mContext, R.string.permission_des_storage, new PermissionUtil.PermissonCallBack() {
            @Override
            public void onDenied() {
                Log.w("TAG", "onDenied");

            }

            @Override
            public void onGranted() {
                Log.w("TAG", "onGranted");

            }
        }, Utils.getPermissions(PERMISSIONS));


    }


    private void toCamera() {
//        Intent intent = new Intent();
//        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
//        startActivity(intent);
        String IMAGE_FILE_NAME = "temp_head_image.jpg";

        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME));
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
    }


    /**
     * 解释权限的dialog
     */
    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(mContext)
                .setTitle("申请权限")
                .setMessage("我们需要" + Arrays.toString(permission) + "权限")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    /**
     * 显示前往应用设置Dialog
     */
    private void showToAppSettingDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RxPermissionUtil.toAppSetting(mContext);
                    }
                })
                .setNegativeButton("取消", null).show();
    }


}
