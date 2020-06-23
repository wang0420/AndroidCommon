package com.android.newcommon.net;


import com.android.common.UrlUtil;
import com.android.newcommon.net.fileLoad.MediaUploadResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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



    /**
     * 上传照片/头像
     * memberId
     **/
    @Multipart
    @POST(UrlUtil.upload_img_new)
    Observable<ZResponse<MediaUploadResponse>> uploadVideo(@Part("memberId") int memberId,/*0无 1后置 2前置*/
                                                           @PartMap() Map<String, RequestBody> maps);
}
