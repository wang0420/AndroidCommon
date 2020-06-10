package com.basemodule.zast.permission;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import io.reactivex.functions.Action;

public class ZAPermission {
    private Fragment mFragment;
    private FragmentActivity mFragmentActivity;
    private String[] mPermissionItems;
    private Action mGrantedAction;
    private Action mDeniedAction;
    private boolean isOverlay = false;

    public static ZAPermission with(FragmentActivity activity) {
        return new ZAPermission(activity);
    }

    public static ZAPermission with(Fragment fragment) {
        return new ZAPermission(fragment);
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

    private ZAPermission(FragmentActivity activity) {
        this.mFragmentActivity = activity;
    }

    private ZAPermission(Fragment fragment) {
        this.mFragment = fragment;
    }

    public ZAPermission permission(String... permissions) {
        this.mPermissionItems = permissions;
        return this;
    }

    public ZAPermission permission(String[]... permissions) {
        return permission(Utils.getPermissions(permissions));
    }

    public ZAPermission overlay() {
        isOverlay = true;
        return this;
    }

    public ZAPermission onGranted(Action action) {
        mGrantedAction = action;
        return this;
    }

    public ZAPermission onDenied(Action action) {
        mDeniedAction = action;
        return this;
    }




}
