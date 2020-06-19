package com.android.common.httpnet;


import okhttp3.Call;

/**
 * Created by wangwei on 2017/3/16.
 */

public interface SyncDataBack {
    /**
     * 请求失败
     *
     * @param request
     */
    void requestFailure(Call request, Exception e);

    /**
     * 请求成功
     *
     * @param result
     */
    void requestSuccess(String result);

}
