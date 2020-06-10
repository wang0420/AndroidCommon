package com.basemodule.za_test.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.basemodule.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import io.reactivex.functions.Consumer;

/**
 * @author hechunshan
 * @date 2017/6/16
 */
public class PermissionUtil {


    public static boolean hasPermission(Context context, String... permissions) {
        return ZAPermission.hasPermissions(context, permissions);
    }

    public static boolean hasStoragePermission(Context context) {
        return ZAPermission.hasPermissions(context, PermissionGroup.STORAGE);
    }

    public static boolean hasPhoneStatePermission(Context context) {
        return ZAPermission.hasPermissions(context, Manifest.permission.READ_PHONE_STATE);
    }

    public static boolean hasLocationPermission(Context context) {
        return ZAPermission.hasPermissions(context, PermissionGroup.LOCATION);
    }

    /**
     * 存储权限申请
     *
     * @param activity
     * @param res
     */

    public static void requestStoragePermission(Context activity, int res, PermissonCallBack callBack) {
        requestPermission(activity, res, callBack, PermissionGroup.STORAGE);
    }

    public static void requestLocationPermission(Context activity, int res, PermissonCallBack callBack) {
        requestPermission(activity, res, callBack, PermissionGroup.LOCATION);
    }

    public static void requestCameraPermission(Context activity, int res, PermissonCallBack callBack) {
        requestPermission(activity, res, callBack, new String[]{
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR});
    }

    public static void requestMicPermission(Context activity, int res, PermissonCallBack callBack) {
        requestPermission(activity, res, callBack, PermissionGroup.MICROPHONE);
    }

    public static void requestSinglePermission(Context activity, int res, String permissions, PermissonCallBack callBack) {
        requestPermission(activity, res, callBack, permissions);
    }


    /**
     * 检测多个权限
     *
     * @return 未授权的权限
     */
    private static List<String> checkMorePermissions(Context context, String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!RxPermissionUtil.checkPermission(context, permissions[i]))
                permissionList.add(permissions[i]);
        }
        return permissionList;
    }

    /**
     * 检测并申请多个权限
     */
    public static void checkAndRequestMorePermissions(Context context, String[] permissions, PermissonCallBack listener) {
        List<String> permissionList = checkMorePermissions(context, permissions);
        if (permissionList.size() == 0) {  // 用户已授予权限
            listener.onGranted();
        } else {
            String[] per = (String[]) permissionList.toArray(new String[permissionList.size()]);
            requestPermission(context, R.string.permission_des_storage, listener, per);
        }
    }


    @SuppressLint("CheckResult")
    public static void requestPermission(Context context, int msg, PermissonCallBack listener, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions((FragmentActivity) context);
        rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                 Log.d("TAG", "accept----- " + granted);
                if (granted) {
                    if (listener != null) {
                        listener.onGranted();
                    }
                } else {
                    if (listener != null) {
                        listener.onDenied();

                    }
                }
            }
        });

   /*     AndPermission.with(context).runtime().permission(Utils.getPermissions(permissions))
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (listener != null) {
                            Log.d("TAG", "onAction(List<String>) called in onGranted, permissions: " + permissions);
                            listener.onGranted();
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        tipDialog(context, msg);
                        if (listener != null) {
                            listener.onDenied();
                            Log.d("TAG", "onAction(List<String>) called in onDenied, data: " + data);
                        }
                    }
                }).start();*/
    }

    /**
     * 判断是否已拒绝过权限
     *
     * @return
     * @describe :如果应用之前请求过此权限但用户拒绝，此方法将返回 true;
     * -----------如果应用第一次请求权限或 用户在过去拒绝了权限请求，
     * -----------并在权限请求系统对话框中选择了 Don't ask again 选项，此方法将返回 false。
     */
    public static boolean judgePermission(Context context, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission))
            return true;
        else
            return false;
    }

    /**
     * 提示对话框
     */
    public static void tipDialog(Context context, int msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示：");
        builder.setMessage(context.getResources().getString(msg));
        builder.setIcon(R.mipmap.add_icon);
        builder.setCancelable(true);            //点击对话框以外的区域是否让对话框消失

        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "你点击了确定", Toast.LENGTH_SHORT).show();
                //  ZAPermission.goSettingPage(context);
                RxPermissionUtil.toAppSetting(context);
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "你点击了取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.e("TAG", "对话框显示了");
            }
        });
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e("TAG", "对话框消失了");
            }
        });
        dialog.show();                              //显示对话框
    }

    /**
     * 判断是否是API 23之后的系统版本
     *
     * @return
     */
    public static boolean isAfterMVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }

    public interface DialogCallBack {
        void dismiss();
    }

    public interface PermissonCallBack {
        default void onDenied() {
            //拒绝
        }

        default void onGranted() {
            //允许
        }
    }
}
