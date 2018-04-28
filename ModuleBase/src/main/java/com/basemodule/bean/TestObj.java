package com.basemodule.bean;

import java.io.Serializable;

/**
 * TODO:Feature
 *
 * @author zhilong <a href="mailto:zhilong.lzl@alibaba-inc.com">Contact me.</a>
 * @version 1.0
 * @since 2017/3/16 下午4:42
 */
public class TestObj implements Serializable {
    public String name;
    public int id;

    public TestObj() {
    }

    public TestObj(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
