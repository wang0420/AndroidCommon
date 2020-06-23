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

import com.android.newcommon.permission.PermissionUtil;
import com.android.newcommon.permission.Utils;
import com.android.newcommon.permission.ui.IPermissionUIAction;
import com.android.newcommon.permission.ui.PermissionBean;
import com.android.newcommon.permission.ui.ZPermissionUI;
import com.module.ui.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        if (id == R.id.btn_style) {
            List<PermissionBean> permissionBeanList = new ArrayList<>();
            permissionBeanList.add(new PermissionBean(Manifest.permission.CAMERA, "录制视频权限", "使用摄像头获取视频信息，方便视频通讯。", R.drawable.permission_ic_camera));
            permissionBeanList.add(new PermissionBean(Manifest.permission.READ_EXTERNAL_STORAGE, "定位权限", "使用定位功能获取位置信息。", R.drawable.permission_ic_location));
            permissionBeanList.add(new PermissionBean(Manifest.permission.WRITE_EXTERNAL_STORAGE, "录制音频权限", "使用麦克风获取音频信息，方便录音", R.drawable.permission_ic_micro_phone));

            new ZPermissionUI(PermissionActivity.this)
                    .title("珍爱需要权限才可以使用")
                    .touchOutsideCancled(true)
                    //.layout(R.layout.view_stub_permission_tip_youth) //自定义弹窗样式
                    .animation(R.style.permission_anim_modal)
                    .permissions(permissionBeanList)
                    .uiAction(new IPermissionUIAction() {
                        @Override
                        public void onCloseClick(ZPermissionUI permissionUI) {
                            System.out.println("onCloseClick");
                        }

                        @Override
                        public void onSubmitClick(ZPermissionUI permissionUI, String[] permissions) {
                            System.out.println("onSubmitClick");
                        }
                    })
                    .onGranted(() -> {
                        System.out.println("全部允许");

                    })
                    .build();
        } else if (id == R.id.btn_main_request_one_permission) {
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


}
