package com.modulec;

import com.contrarywind.adapter.WheelAdapter;

import java.util.List;

/**
 * Created by wangwei on 2018/4/19.
 */


public class ArrayWheelAdapter<T> implements WheelAdapter {
    public static final int DEFAULT_LENGTH = 4;
    private List<T> items;
    private int length;

    public ArrayWheelAdapter(List<T> items, int length) {
        this.items = items;
        this.length = length;
    }

    public ArrayWheelAdapter(List<T> items) {
        this(items, 4);
    }

    public Object getItem(int index) {
        return index >= 0 && index < this.items.size() ? this.items.get(index) : "";
    }

    public int getItemsCount() {
        return this.items.size();
    }

    public int indexOf(Object o) {
        return this.items.indexOf(o);
    }
}
