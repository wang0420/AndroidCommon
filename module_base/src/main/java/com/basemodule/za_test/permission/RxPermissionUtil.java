package com.basemodule.za_test.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * 权限申请库，对 RxPermission 和 UI(Dialog) 进行封装
 *
 * @author ChengGuo
 * @date 2019/5/16
 */
public final class RxPermissionUtil {


    /**
     * 检测权限
     *
     * @return true：已授权； false：未授权；
     */
    public static boolean checkPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }


    /**
     * 检测权限
     *
     * @return true：已授权； false：未授权；
     */
    public static boolean hasPermissions(@NonNull Context context, @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm)
                    == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }

    /**
     * 跳转到权限设置界面
     */
    public static void toAppSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }


    private static List<String> getDeniedPermissions(Context context, String[] permissions) {
        List<String> deniedPermissions = new ArrayList();
        if (context != null) {
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(context, permissions[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permissions[i]);
                }
            }
        }
        return deniedPermissions;
    }

    /**
     * Android M之前版本，检测是否能录音
     *
     * @return true 能，false 不能
     */
    public static boolean canRecordAudioBeforeAndroidM() {
        int audioSource = MediaRecorder.AudioSource.MIC;
        int sampleRateInHz = 44100;
        int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

        int bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig,
                audioFormat);
        try {
            AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz,
                    channelConfig, audioFormat, bufferSizeInBytes);
            audioRecord.startRecording();
            if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                return false;
            }
            audioRecord.stop();
            audioRecord.release();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Android M之前，检测是否能使用摄像头
     *
     * @return true 能，false 不能
     */
    public static boolean canUseCameraBeforeAndroidM() {
        boolean can = true;
        Camera camera = null;
        try {
            camera = Camera.open(0);
            Camera.Parameters params = camera.getParameters();
            camera.setParameters(params);
        } catch (Exception e) {
            can = false;
        }

        if (camera != null) {
            try {
                camera.stopPreview();
                camera.release();
            } catch (Exception e) {
                return can;
            }
        }
        return can;
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
}
