package com.module.user.ativites;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.basemodule.utils.PermissionUtils;
import com.module.user.R;

import java.io.File;
import java.util.Arrays;

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    // 相机权限、多个权限
    private final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    private final String[] PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CALENDAR};

    // 打开相机请求Code，多个权限请求Code
    private final int REQUEST_CODE_CAMERA = 1, REQUEST_CODE_PERMISSIONS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_premission);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_main_request_one_permission) {// AppRequestPermission.checkPermission(this);
            requestPermission();
        } else if (id == R.id.btn_main_request_permissions) {
            requestMorePermissions();
        } else if (id == R.id.btn_main_request_one_permission1) {
            requestPermission1();
        } else if (id == R.id.btn_main_request_permissions1) {
            requestMorePermissions1();
        }
    }

    // 普通申请一个权限
    private void requestPermission() {
        PermissionUtils.checkAndRequestPermission(mContext, PERMISSION_CAMERA, REQUEST_CODE_CAMERA,
                new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        // 权限已被授予
                        toCamera();
                    }
                });
    }

    // 自定义申请一个权限
    private void requestPermission1() {
        PermissionUtils.checkPermission(mContext, PERMISSION_CAMERA,
                new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        toCamera();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showExplainDialog(permission, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PermissionUtils.requestPermission(mContext, PERMISSION_CAMERA, REQUEST_CODE_CAMERA);
                            }
                        });
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        PermissionUtils.requestPermission(mContext, PERMISSION_CAMERA, REQUEST_CODE_CAMERA);
                    }
                });
    }

    // 普通申请多个权限
    private void requestMorePermissions() {
        PermissionUtils.checkAndRequestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS,
                new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        // 权限已被授予
                        toCamera();
                    }
                });
    }

    // 自定义申请多个权限
    private void requestMorePermissions1() {
        PermissionUtils.checkMorePermissions(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                toCamera();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                PermissionUtils.requestMorePermissions(mContext, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });
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
                        PermissionUtils.toAppSetting(mContext);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (PermissionUtils.isPermissionRequestSuccess(grantResults)) {
                    // 权限申请成功
                    toCamera();
                } else {
                    Toast.makeText(mContext, "打开相机失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_PERMISSIONS:
                PermissionUtils.onRequestMorePermissionsResult(mContext, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        toCamera();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        Toast.makeText(mContext, "我们需要" + Arrays.toString(permission) + "权限", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        Toast.makeText(mContext, "我们需要" + Arrays.toString(permission) + "权限", Toast.LENGTH_SHORT).show();
                        showToAppSettingDialog();
                    }
                });


        }
    }


}
