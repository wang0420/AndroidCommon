package com.android.newcommon.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class ApplicationDetailsSetting {
    private static final int REQUEST_CODE_SETTING = 1;
    private static final String MARK = Build.MANUFACTURER.toLowerCase();

    private Activity mContext;

    private ApplicationDetailsSetting(Context context) {
        this.mContext = Utils.findActivity(context);
        start(REQUEST_CODE_SETTING);
    }

    private ApplicationDetailsSetting(Context context, int requestCode) {
        this.mContext = Utils.findActivity(context);
        start(requestCode);
    }

    public static void goSetting(Context context ){
        new ApplicationDetailsSetting(context);
    }

    public static void goSetting(Context context, int requestCode){
        new ApplicationDetailsSetting(context,requestCode);
    }

    /**
     * Start.
     *
     * @param requestCode this code will be returned in onActivityResult() when the activity exits.
     */
    private void start(int requestCode) {
        Intent intent;
        if (MARK.contains("huawei")) {
            intent = huaweiApi(mContext);
        } else if (MARK.contains("xiaomi")) {
            intent = xiaomiApi(mContext);
        } else if (MARK.contains("oppo")) {
            intent = oppoApi(mContext);
        } else if (MARK.contains("vivo")) {
            intent = vivoApi(mContext);
        } else if (MARK.contains("meizu")) {
            intent = meizuApi(mContext);
        } else {
            intent = defaultApi(mContext);
        }
        try {
            mContext.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            intent = defaultApi(mContext);
            mContext.startActivityForResult(intent, requestCode);
        }
    }

    private static Intent defaultApi(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    private static Intent huaweiApi(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        if (hasActivity(context, intent)) {
            return intent;
        }

        return defaultApi(context);
    }

    private static Intent xiaomiApi(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.putExtra("extra_pkgname", context.getPackageName());
        if (hasActivity(context, intent)) {
            return intent;
        }

        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        if (hasActivity(context, intent)) {
            return intent;
        }

        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        if (hasActivity(context, intent)) {
            return intent;
        }

        return defaultApi(context);
    }

    private static Intent vivoApi(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packagename", context.getPackageName());
        intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        if (hasActivity(context, intent)) {
            return intent;
        }

        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity");
        if (hasActivity(context, intent)) {
            return intent;
        }

        return defaultApi(context);
    }

    private static Intent oppoApi(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        if (hasActivity(context, intent)) {
            return intent;
        }

        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
        if (hasActivity(context, intent)) {
            return intent;
        }

        return defaultApi(context);
    }

    private static Intent meizuApi(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.putExtra("packageName", context.getPackageName());
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        if (hasActivity(context, intent)) {
            return intent;
        }

        return defaultApi(context);
    }

    private static boolean hasActivity(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}