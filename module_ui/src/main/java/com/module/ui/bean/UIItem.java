package com.module.ui.bean;

/**
 * Created by wangwei on 2020/5/13.
 */

public class UIItem {
    private String title;
    private Class<?> activity;



    public UIItem(String title, Class<?> activity) {
        this.title = title;
        this.activity = activity;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getActivity() {
        return activity;
    }

    public void setActivity(Class<?> activity) {
        this.activity = activity;
    }

}
