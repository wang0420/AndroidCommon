package com.android.newcommon.net.manager;

import com.android.newcommon.net.fileLoad.upload.UploadManagerBuilder;
import com.android.newcommon.net.fileLoad.upload.entity.UploadInfo;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;


public class RequestManager {

    private LifecycleProvider lifecycleProvider;

    public RequestManager(LifecycleProvider lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public <T> SingleRequestManagerBuilder<T> api(Observable<T> observable) {
        SingleRequestManagerBuilder<T> builder = new SingleRequestManagerBuilder<>();
        builder.api(observable).setLifecycle(lifecycleProvider);
        lifecycleProvider = null;
        return builder;
    }


    public <T> UploadManagerBuilder<T> upload(UploadInfo<T> uploadInfo) {
        UploadManagerBuilder<T> uploadManagerBuilder = new UploadManagerBuilder<>();
        uploadManagerBuilder.api(uploadInfo).setLifecycle(lifecycleProvider);
        lifecycleProvider = null;
        return uploadManagerBuilder;
    }



  /*  public DownloadManagerBuilder download(DownloadInfo info) {
        DownloadManagerBuilder builder = new DownloadManagerBuilder(info).setLifecycleProvider(lifecycleProvider);
        lifecycleProvider = null;
        return builder;
    }
*/
}
