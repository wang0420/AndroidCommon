package com.basemodule.ww_test.permission.ui;

import java.io.Serializable;

public class PermissionBean implements Serializable {
    public String name;
    public String desc;
    public String type;
    public int iconRes;

    public PermissionBean(String type, String name, String desc, int iconRes) {
        this.desc = desc;
        this.name = name;
        this.iconRes = iconRes;
        this.type = type;
    }
}
