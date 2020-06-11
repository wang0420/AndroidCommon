package com.basemodule.ww_test.net;


import java.io.Serializable;
import java.util.ArrayList;


public class ZResponse<T> extends BaseEntity {
    public String timestamp;
    public int code;
    public T data;
    public String msg;


    @Override
    public String[] uniqueKey() {
        return new String[0];
    }

    public static class Data implements Serializable {
        public String data;
    }

    public static class ListData<T> extends Data {
        public boolean hasNext;
        public ArrayList<T> list;
    }
}


