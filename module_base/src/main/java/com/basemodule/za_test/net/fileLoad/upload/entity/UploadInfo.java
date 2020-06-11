package com.basemodule.za_test.net.fileLoad.upload.entity;

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

    public UploadInfo(FileAndParamName fileAndParamName) {
        fileAndParamNames.clear();
        fileAndParamNames.add(fileAndParamName);
        initSize();
    }

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

    public static class FileAndParamName {
        public File file;
        public String paramName;

        public FileAndParamName(File file, String paramName) {
            this.file = file;
            this.paramName = paramName;
        }
    }
}
