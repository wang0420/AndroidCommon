package com.android.newcommon.utils;

import android.text.TextUtils;

import com.android.common.BaseApplication;
import com.android.newcommon.utils.FileUtils;

import java.io.File;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * LuBan
 * 图片压缩工具类
 * https://blog.csdn.net/qq_27634797/article/details/79424507
 */

public class LuBanUtils {
    private static String folderName = "ImagePhoto";

    public static void LuBanCompressImage(File file, final CompressListener compressListener) {
        Luban.with(BaseApplication.getInstance())
                .load(file) // 传人要压缩的图片
                .ignoreBy(50)  // 忽略不压缩图片的大小
                .setTargetDir(FileUtils.getInstance().createFolder(folderName).getAbsolutePath())// 设置压缩后文件存储位置
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                compressListener.onStart();
            }

            @Override
            public void onSuccess(File file) {
                // TODO 压缩成功后调用，返回压缩后的图片文件
                compressListener.onSuccess(file);
            }

            @Override
            public void onError(Throwable e) {
                // TODO 当压缩过程出现问题时调用
                compressListener.onError();
            }
        }).launch();//启动压缩

    }

    public static void LuBanCompressImage(String urlPath, final CompressListener compressListener) {
        Luban.with(BaseApplication.getInstance())
                .load(urlPath) // 传人要压缩的图片
                .ignoreBy(50)  // 忽略不压缩图片的大小
                .setTargetDir(FileUtils.getInstance().createFolder(folderName).getAbsolutePath())// 设置压缩后文件存储位置
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                compressListener.onStart();
            }

            @Override
            public void onSuccess(File file) {
                // TODO 压缩成功后调用，返回压缩后的图片文件
                compressListener.onSuccess(file);
            }

            @Override
            public void onError(Throwable e) {
                // TODO 当压缩过程出现问题时调用
                compressListener.onError();
            }
        }).launch();//启动压缩

    }

    /*
     * 压缩成功或失败回掉
     * */
    public interface CompressListener {
        default void onStart() {
        }

        void onSuccess(File file);

        void onError();
    }
}
