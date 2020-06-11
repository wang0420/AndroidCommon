package com.basemodule.za_test.net;


import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LoginService {

    @POST("/api/cupid/login/send-message-code")
    Observable<ZAResponse<ZAResponse.Data>> login(@Body HashMap<String, Object> requestBody);

}
