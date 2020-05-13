package com.module.ui.util;

import com.module.ui.bean.MemberBean;

import java.util.Comparator;

// 专用于按首字母排序
public class PinyinComparator implements Comparator<MemberBean> {

    public int compare(MemberBean o1, MemberBean o2) {
        if (o1.getLetters().equals("@") || o2.getLetters().equals("#")) {
            return -1;
        } else if (o1.getLetters().equals("#") || o2.getLetters().equals("@")) {
            return 1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }

}
