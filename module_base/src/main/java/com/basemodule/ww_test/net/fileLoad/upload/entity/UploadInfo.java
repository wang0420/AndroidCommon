package com.basemodule.ww_test.net.fileLoad.upload.entity;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by XingjieZheng
 * on 2017/6/7.
 */
public abstract class UploadInfo<T> {

    long size = 0;
    private List<FileAndParamName> fileAndParamNames = new ArrayList<>();

    //单个文件
    public UploadInfo(FileAndParamName fileAndParamName) {
        fileAndParamNames.clear();
        fileAndParamNames.add(fileAndParamName);
        initSize();
    }

    //多个文件
    public UploadInfo(List<FileAndParamName> fileAndParamNames) {
        if (fileAndParamNames == null || fileAndParamNames.isEmpty()) {
            return;
        }
        this.fileAndParamNames.clear();
        this.fileAndParamNames.addAll(fileAndParamNames);
        initSize();
    }

    private void initSize() {
        for (FileAndParamName fileAndParamName : fileAndParamNames) {
            size += fileAndParamName.file.length();
        }
        Log.w("TAG", size + "---size-----");

    }

    public long getFilesTotalSize() {
        return size;
    }

    public int getFileNum() {
        return fileAndParamNames.size();
    }

    public List<FileAndParamName> getFileAndParamNames() {
        return fileAndParamNames;
    }

    public abstract Observable<T> getApi(HashMap<String, RequestBody> params);


}
