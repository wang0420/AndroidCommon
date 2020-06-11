package com.basemodule.za_test.net;


import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface LoginService {

    @POST("")
    Observable<ZAResponse<MessageCodeEntity>> login(@Body HashMap<String, Object> requestBody);

    @GET("")
    Observable<ZAResponse<String>> template(@QueryMap HashMap<String, Object> requestBody);

}
