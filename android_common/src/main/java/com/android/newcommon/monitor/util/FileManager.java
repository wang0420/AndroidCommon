package com.android.newcommon.monitor.util;

import android.os.Environment;
import android.text.TextUtils;

import com.android.newcommon.monitor.LogHelper;
import com.android.newcommon.monitor.crash.CrashInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileManager {
    private static final String TAG = "FileManager";

    public static final String ZAFOLDER = "aw";//根目录

    public static String BLOCK_DIR_NAME = "block";
    public static final String PHOTO_DIR_NAME = "photo";
    public static final String VIDEO_DIR_NAME = "video";
    public static final String CRASH_DIR_NAME = "crash";

    /**
     * app文件根目录
     */
    private static File getRootFolder() {
        File file = new File(Environment.getExternalStorageDirectory(), ZAFOLDER);
        if (file.exists()) {
            if (!file.isDirectory()) {
                file.delete();
                file.mkdirs();
            }
        } else {
            file.mkdirs();
        }
        return file;
    }

    /**
     * crash 文件夹
     */
    public static File getCrashDir() {
        String path = getRootFolder() + File.separator + CRASH_DIR_NAME;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    /**
     * 获取历史缓存Crash文件
     */
    public static List<CrashInfo> getCrashCaches() {
        File[] caches = getCrashDir().listFiles();
        List<CrashInfo> result = new ArrayList<>();
        if (caches == null) {
            return result;
        }
        for (File cache : caches) {
            StringBuilder content = FileManager.readFile(cache.getAbsolutePath(), "utf-8");
            CrashInfo crashInfo = new CrashInfo(content.toString(), cache.lastModified());
            result.add(crashInfo);
        }
        return result;
    }


    /**
     * Block 文件夹
     */
    public static File getBlockDir() {
        String path = getRootFolder() + File.separator + BLOCK_DIR_NAME;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    /**
     * 删除文件夹
     *
     * @param file
     */
    public static void deleteDirectory(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File f : listFiles) {
                deleteDirectory(f);
            }
            file.delete();
        } else {
            file.delete();
        }
    }


    /**
     * 将字符串写入到文本文件中
     * filePath  文件夹路径
     */
    public static void writeTxtToFile(String strContent, String filePath) {
        if (TextUtils.isEmpty(strContent)) {
            return;
        }
        try {
            //创建文件
            File file = new File(filePath);
            if (!file.exists()) {
                LogHelper.d(TAG, "Create the file:" + filePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            LogHelper.e(TAG, "Error on write File:" + e);
        }
    }

    /**
     * 文件名称已时间命名
     */
    public static String createFile() {
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String time = formatter.format(new Date());
        return time + ".txt";
    }


    /**
     * read file
     *
     * @param filePath    文件路径
     * @param charsetName The name of a supported {@link java.nio.charset.Charset
     *                    </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * 获取文件大小
     * Formatter.formatFileSize(ANRActivity.this, FileManager.getDirectorySize(FileManager.getCrashDir()));
     * 单位KB
     */
    public static long getDirectorySize(File directory) {
        long size = 0;
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            return size;
        }
        for (File file : listFiles) {
            if (file.isDirectory()) {
                size += getDirectorySize(file);
            } else {
                size += file.length();
            }
        }
        return size;
    }

}
