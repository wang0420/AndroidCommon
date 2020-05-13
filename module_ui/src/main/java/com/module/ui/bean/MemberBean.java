package com.module.ui.bean;


/**
 * Created by wangwei on 2018/3/16.
 */

public class MemberBean {

    private String name;
    private String letters;//显示拼音的首字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }
}