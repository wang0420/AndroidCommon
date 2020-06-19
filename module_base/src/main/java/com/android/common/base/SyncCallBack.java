package com.android.common.base;



/**
 * Created by wangwei on 2018/3/27.
 * M层请求数据后  回调P层通用接口
 */

public interface SyncCallBack<M> {
    void onSuccess(M successData);

    void onError(int var1, String var2);
}
