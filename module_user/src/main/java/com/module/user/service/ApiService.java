package com.module.user.service;


import com.basemodule.base.BaseRespond;
import com.module.user.bean.DataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 */
public interface ApiService {
 // https://www.wanandroid.com/article/list/0/json?cid=60
    @GET("/article/list/0/json")
    Observable<BaseRespond<DataBean>> getListData(@Query("cid") int cid);


}
