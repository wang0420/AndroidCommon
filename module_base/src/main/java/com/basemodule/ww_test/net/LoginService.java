package com.basemodule.ww_test.net;


import com.basemodule.UrlUtil;
import com.basemodule.ww_test.net.fileLoad.MediaUploadResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;


public interface LoginService {

    @POST(UrlUtil.url1)
    Observable<ZResponse<MessageCodeEntity>> login(@Body HashMap<String, Object> requestBody);

    @GET(UrlUtil.url2)
    Observable<ZResponse<String>> template(@QueryMap HashMap<String, Object> requestBody);


    /**
     * 上传照片/头像
     * turn:0无 1后置 2前置
     **/
    @Multipart
    @POST(UrlUtil.upload_img_new)
    Observable<ZResponse<MediaUploadResponse>> upload(@Part List<MultipartBody.Part> parts);


}
