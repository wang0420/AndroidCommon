package com.basemodule.ww_test.net;

/**
 * Created by wangwei on 2020/1/17.
 * 短信验证码
 */

public class MessageCodeEntity extends  BaseEntity{

    /**
     * isRegister : false
     * sendSmsStatus : true
     * sendSmsErrMsg : null
     */

    public boolean isRegister;
    public boolean sendSmsStatus;
    public String sendSmsErrMsg;

    @Override
    public String[] uniqueKey() {
        return new String[0];
    }
}