package com.example.module.tablayout.multiple_list.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.module.tablayout.multiple_list.ExpandableItemAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class Person implements MultiItemEntity {
    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public String name;
    public int age;

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_PERSON;
    }
}