package com.android.newcommon.permission;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import io.reactivex.functions.Action;

public class ZPermission {
    private Fragment mFragment;
    private FragmentActivity mFragmentActivity;
    private String[] mPermissionItems;
    private Action mGrantedAction;
    private Action mDeniedAction;
    private boolean isOverlay = false;

    public static ZPermission with(FragmentActivity activity) {
        return new ZPermission(activity);
    }

    public static ZPermission with(Fragment fragment) {
        return new ZPermission(fragment);
    }

    public static boolean hasPermissions(@NonNull Context context, @NonNull String... perms) {
        return RxPermissionUtil.hasPermissions(context, perms);
    }

    public static boolean hasPermissions(@NonNull Context context, @NonNull String[]... perms) {
        return RxPermissionUtil.hasPermissions(context, Utils.getPermissions(perms));
    }

    public static void goSettingPage(@NonNull Context context) {
        ApplicationDetailsSetting.goSetting(context);
    }

    public static void goSettingPage(@NonNull Context context, int requestCode) {
        ApplicationDetailsSetting.goSetting(context, requestCode);
    }

    private ZPermission(FragmentActivity activity) {
        this.mFragmentActivity = activity;
    }

    private ZPermission(Fragment fragment) {
        this.mFragment = fragment;
    }

    public ZPermission permission(String... permissions) {
        this.mPermissionItems = permissions;
        return this;
    }

    public ZPermission permission(String[]... permissions) {
        return permission(Utils.getPermissions(permissions));
    }

    public ZPermission overlay() {
        isOverlay = true;
        return this;
    }

    public ZPermission onGranted(Action action) {
        mGrantedAction = action;
        return this;
    }

    public ZPermission onDenied(Action action) {
        mDeniedAction = action;
        return this;
    }




}
