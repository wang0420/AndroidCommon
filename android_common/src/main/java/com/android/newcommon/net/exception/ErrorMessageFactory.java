package com.android.newcommon.net.exception;


import com.android.common.BaseApplication;
import com.android.common.R;

public class ErrorMessageFactory {

    public static String create(int code) {
        String errorMsg = null;
        switch (code) {
            case ApiException.HTTP_ERROR:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.noNet);
                break;
            case ApiException.SOCKET_TIMEOUT_ERROR:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_socket_timeout);
                break;
            case ApiException.HTTP_CODE_101:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_http_101);
                break;
            case ApiException.HTTP_CODE_102:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_http_102);
                break;
            case ApiException.SOCKET_ERROR:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_socket_unreachable);
                break;
            case ApiException.REVERT_ERROR:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.revert_error);
                break;
            case ApiException.ERROR_HTTP_400:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_http_400);
                break;
            case ApiException.ERROR_HTTP_404:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_http_404);
                break;
            case ApiException.ERROR_HTTP_500:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_http_500);
                break;
            case ApiException.ERROR_API_SYSTEM:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_system);
                break;
            case ApiException.ERROR_TOKEN:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_token);
                break;
            case ApiException.JSON_ERROR:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.revert_error);
                break;
            default:
                errorMsg = BaseApplication.getInstance().getResources().getString(R.string.error_unkown);
                break;

        }
        return errorMsg;
    }
}
