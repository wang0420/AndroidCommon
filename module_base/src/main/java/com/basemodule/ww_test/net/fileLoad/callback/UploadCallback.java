package com.basemodule.ww_test.net.fileLoad.callback;

import com.basemodule.ww_test.net.utils.Callback;


public abstract class UploadCallback<T> extends Callback<T> {

    /**
     * 上传文件进度
     *
     * @param index                   当前文件位置
     * @param allAddUpProgress        多个文件累计进度
     * @param allTotalProgress        所有文件的进度总量
     * @param currentOneProgress      当前文件进度
     * @param currentOneTotalProgress 当前文件的进度总量
     * @param done                    是否全部完成
     */
    public void onProgress(int index, long allAddUpProgress, long allTotalProgress,
                           long currentOneProgress, long currentOneTotalProgress, boolean done) {
    }

}
