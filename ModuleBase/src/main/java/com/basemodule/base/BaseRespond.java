package com.basemodule.base;

/**
 * 网络请求结果 基类,剥离出数据给上层
 */
public class BaseRespond<T> {

    private int errorCode;
    private String errorMsg;
    private T data;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
