package com.android.newcommon.net.exception;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.HttpException;


public class RxErrorHandler {

    public ApiException handleError(Throwable e) {
        ApiException exception = new ApiException();
        if (e instanceof JSONException) {
            exception.setCode(ApiException.JSON_ERROR);
        } else if (e instanceof IllegalStateException) {
            exception.setCode(ApiException.JSON_ERROR);
        } else if (e instanceof HttpException) {
            exception.setCode(((HttpException) e).code());
        } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
            exception.setCode(ApiException.SOCKET_TIMEOUT_ERROR);
        } else if (e instanceof ConnectException || e instanceof ConnectTimeoutException) {
            exception.setCode(ApiException.HTTP_ERROR);
        } else if (e instanceof UnknownHostException || e instanceof UnknownServiceException) {
            exception.setCode(ApiException.HTTP_ERROR);
        } else if (e instanceof NumberFormatException) {
            exception.setCode(ApiException.REVERT_ERROR);
        } else {
            exception.setCode(ApiException.UNKNOWN_ERROR);
        }
        exception.setMsg(ErrorMessageFactory.create(exception.getCode()));
        return exception;
    }

}
