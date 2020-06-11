package com.basemodule.za_test.net;


import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface LoginService {

    @POST("/api/cupid/login/send-message-code")
    Observable<ZAResponse<MessageCodeEntity>> login(@Body HashMap<String, Object> requestBody);

    @GET("/api/cupid/recommend/message-template")
    Observable<ZAResponse<String>> template(@QueryMap HashMap<String, Object> requestBody);

}
