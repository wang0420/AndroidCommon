package com.android.common.ww_test.permission;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

/**
 * User: newSalton@outlook.com
 * Date: 2019/6/4 17:43
 * ModifyTime: 17:43
 * Description:
 */
public class Utils {
    public static String[] getPermissions(String[]... strings) {
        List<String> list = new ArrayList<>();
        for (String[] perm : strings) {
            list.addAll(Arrays.asList(perm));
        }
        return list.toArray(new String[]{});
    }

    public static FragmentActivity findActivity(Context context) {
        if (context instanceof Activity) {
            return (FragmentActivity) context;
        }
        if (context instanceof ContextWrapper) {
            ContextWrapper wrapper = (ContextWrapper) context;
            return findActivity(wrapper.getBaseContext());
        } else {
            return null;
        }
    }

    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }

    public static boolean isStorageCanUse(Context context) {
        boolean isCanUse = true;

        File temp = new File(getDefaultSDCardFilePath(context), "temp");

        try {
            temp.createNewFile();
        } catch (IOException e) {
            isCanUse = false;
        }
        if (temp.exists()) {
            temp.delete();
        }
        return isCanUse;
    }

    public static boolean isAudioCanUse(Context context) {
        boolean isCanUse;

        File temp = new File(getAudioDir(context), "temp.aac");

        MediaRecorder recorder = new MediaRecorder();
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            recorder.setAudioChannels(1);
            recorder.setAudioSamplingRate(8000);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setOutputFile(temp.getPath());//这里给个假的地址,因为这段录音是无效的.
            recorder.prepare();
            recorder.start();//要开始录音时,这里就会弹出提示框了,如果不给权限.我们有异常处理,而且下次想录音时 还是会有此提示.
            isCanUse = true;
        } catch (Exception e) {
            isCanUse = false;
        } finally {
            try {
                recorder.stop();
                recorder.release();
            } catch (Exception e) {
                // nop
            }
            if (temp.exists()) {
                temp.delete();
            } else {
                isCanUse = false;
            }
        }

        return isCanUse;
    }

    private static String getDefaultSDCardFilePath(Context context) {
        String dir = Environment.getExternalStorageDirectory() + File.separator + context.getPackageName() + File.separator;
        try {
            new File(dir, ".nomedia").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dir;
    }

    private static String getAudioDir(Context context) {
        String filePath = getDefaultSDCardFilePath(context) + "audio" + File.separator;
        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }
        return filePath;
    }
}
