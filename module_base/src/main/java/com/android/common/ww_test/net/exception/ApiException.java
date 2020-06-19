package com.android.common.ww_test.net.exception;


public class ApiException extends Exception {

    /*API错误*/
    public static final int API_ERROR = 0x0;
    /*网络错误*/
    public static final int NETWORD_ERROR = 0x1;
    /*http_错误*/
    public static final int HTTP_ERROR = 0x2;
    /*http_错误*/
    public static final int REVERT_ERROR = 0x9;
    /*json错误*/
    public static final int JSON_ERROR = 0x3;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 0x4;
    /*运行时异常-包含自定义异常*/
    public static final int RUNTIME_ERROR = 0x5;
    /*无法解析该域名*/
    public static final int UNKOWNHOST_ERROR = 0x6;
    /*连接网络超时*/
    public static final int SOCKET_TIMEOUT_ERROR = 0x7;
    /*无网络连接*/
    public static final int SOCKET_ERROR = 0x8;
    // 服务器错误
    public static final int ERROR_API_SYSTEM = 10000;
    // 登录错误，用户名密码错误
    public static final int ERROR_API_LOGIN = 10001;
    //Token 失效
    public static final int ERROR_TOKEN = 10010;
    public static final int HTTP_CODE_101 = 101;
    public static final int HTTP_CODE_102 = 102;

    public static final int ERROR_HTTP_400 = 400;

    public static final int ERROR_HTTP_404 = 404;

    public static final int ERROR_HTTP_405 = 405;

    public static final int ERROR_HTTP_500 = 500;

    private int code;

    private String msg;


    public ApiException() {
    }

    public ApiException(int code, String errMsg) {
        this.code = code;
        this.msg = errMsg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
