package com.basemodule.ww_test.net;


import com.basemodule.UrlUtil;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface LoginService {

    @POST(UrlUtil.url1)
    Observable<ZResponse<MessageCodeEntity>> login(@Body HashMap<String, Object> requestBody);

    @GET(UrlUtil.url2)
    Observable<ZResponse<String>> template(@QueryMap HashMap<String, Object> requestBody);

}
