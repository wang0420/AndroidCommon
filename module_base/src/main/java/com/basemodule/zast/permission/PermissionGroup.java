package com.basemodule.zast.permission;

import android.Manifest;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限组
 * <p>
 * 假设某个应用在其清单中列出READ_EXTERNAL_STORAGE和WRITE_EXTERNAL_STORAGE。
 * 应用请求READ_EXTERNAL_STORAGE，并且用户授予了该权限，如果该应用针对的是API级别24或更低级别，
 * 系统还会同时授予WRITE_EXTERNAL_STORAGE，因为该权限也属于STORAGE权限组并且也在清单中注册过。
 * 如果该应用针对的是Android O，则系统此时仅会授予READ_EXTERNAL_STORAGE，
 * 不过在该应用以后申请WRITE_EXTERNAL_STORAGE权限时，系统会立即授予该权限，而不会提示用户。
 * 但是若没有申请WRITE_EXTERNAL_STORAGE权限，而去进行写存储卡的操作的时候，就会引起应用的崩溃。
 * <p>
 * 对Android O运行时权限策略变化的应对方案
 * 针对Android O 的运行是的权限特点，我们可以在申请权限的时候要申请权限数组，而不是单一的某一个权限。
 * 所以按照上面的危险权限列表我们给系统权限进行分类，把一个组的常量放到数组中，并根据系统版本进行赋值。
 *
 * @author ChengGuo
 * @date 2019/5/23
 */
public class PermissionGroup {

    public static final String[] CALENDAR;
    public static final String[] CAMERA;
    public static final String[] CONTACTS;
    public static final String[] LOCATION;
    public static final String[] MICROPHONE;
    public static final String[] PHONE;
    public static final String[] SENSORS;
    public static final String[] SMS;
    public static final String[] STORAGE;

    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CALENDAR = new String[]{};
            CAMERA = new String[]{};
            CONTACTS = new String[]{};
            LOCATION = new String[]{};
            MICROPHONE = new String[]{};
            PHONE = new String[]{};
            SENSORS = new String[]{};
            SMS = new String[]{};
            STORAGE = new String[]{};
        } else {
            CALENDAR = new String[]{
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR};

            CAMERA = new String[]{
                    Manifest.permission.CAMERA};

            CONTACTS = new String[]{
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.GET_ACCOUNTS};

            LOCATION = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};

            MICROPHONE = new String[]{
                    Manifest.permission.RECORD_AUDIO};

            PHONE = new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.USE_SIP,
                    Manifest.permission.PROCESS_OUTGOING_CALLS};

            SENSORS = new String[]{
                    Manifest.permission.BODY_SENSORS};

            SMS = new String[]{
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_WAP_PUSH,
                    Manifest.permission.RECEIVE_MMS};

            STORAGE = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
    }


}
