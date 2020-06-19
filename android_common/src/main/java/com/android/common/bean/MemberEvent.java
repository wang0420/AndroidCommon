package com.android.common.bean;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class MemberEvent {

    private String message;

    public MemberEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
