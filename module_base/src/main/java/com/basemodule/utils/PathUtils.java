package com.basemodule.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by mayaolan on 2016/1/20.
 */
public class PathUtils {


    private static File storageDir = null;
    private static PathUtils instance = null;
    private File voicePath = null;


    public static PathUtils getInstance() {
        if (instance == null) {
            instance = new PathUtils();
        }

        return instance;
    }

    /**
     * 创建不同的文件夹
     */
    public void initAndroidDataDirs(Context context) {
        String packageName = context.getPackageName();

        /**
         * 创建文件夹以包名开头，在sd卡目录  /storage/emulated/0/packageName/
         * mkdir（）创建此抽象路径名称指定的目录（及只能创建一级的目录，且需要存在父目录）
         *  mkdirs（）创建此抽象路径指定的目录，包括所有必须但不存在的父目录。（及可以创建多级目录，无论是否存在父目录）
         */
        this.voicePath = new File(Environment.getExternalStorageDirectory(), "/ZhenAiWang/voice/");
        Log.w("wangwei", "---->>-" + voicePath.getPath());
        Log.w("wangwei:", "---->>-" + voicePath.getAbsolutePath());


        if (!this.voicePath.exists()) {
            this.voicePath.mkdirs();
        }

        createPhotoFolder("wangweill");

    }

    public static File createPhotoFolder(String folderName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(Environment.getExternalStorageDirectory() + "/" + folderName);
            if (!dir.exists()) {
                dir.mkdir();
            } else if (!dir.isDirectory()) {
                dir.delete();
                dir.mkdir();
            }
            return dir;
        } else {
            return null;
        }
    }

    /**
     * 在某一个目录下创建文件
     * 本例在image目录
     */
    public void creatFile(String fileName) {
        try {
            File dirFile = PathUtils.getInstance().getVoicePath();
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            fileName = fileName + ".txt";
            File file = new File(getVoicePath(), fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    // 生成文件，并写入内容
    public File makeFilePath(String filePath, String fileName) {
        String strContent = "kjadnwdnjsw";
        File file = null;
        try {
            makeRootDirectory(filePath);//如果文件夹不存在，先创建文件夹
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }


    public File getVoicePath() {
        return this.voicePath;
    }

    //sd卡路徑
    private static File getStorageDir(Context mContext) {
        if (storageDir == null) {
            File var1 = Environment.getExternalStorageDirectory();
            if (var1.exists() && var1.canWrite()) {
                return var1;
            }
            storageDir = mContext.getFilesDir();
        }
        Log.w("TAG", "storageDir------->" + storageDir);
        return storageDir;
    }

}
