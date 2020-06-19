package com.android.common.ww_test.net;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.Serializable;


public abstract  class BaseEntity implements Serializable {

    private static final long serialVersionUID = -5653779708018744901L;

    public String toString() {
        return new Gson().toJson(this);
    }

    /**
     * 是否是同一个对象的判断条件
     */
    public abstract String[] uniqueKey();

    @Override
    public boolean equals(Object o) {
        String[] uniqueKey = uniqueKey();
        if (uniqueKey != null && uniqueKey.length > 0) {
            BaseEntity user = (BaseEntity) o;
            String[] modelKey = user.uniqueKey();
            for (int i = 0; i < uniqueKey.length; i++) {
                String unique = uniqueKey[i];
                if (TextUtils.isEmpty(unique) || !unique.equals(modelKey[i])) {
                    return super.equals(o);
                }
            }
            return true;
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        String[] uniqueKey = uniqueKey();
        if (uniqueKey != null && uniqueKey.length > 0) {
            int result = 0;
            for (int i = 0; i < uniqueKey.length; i++) {
                String unique = uniqueKey[i];
                if (!TextUtils.isEmpty(unique)) {
                    result = 31 * result + unique.hashCode();
                }
            }
            if (result == 0) {
                return super.hashCode();
            } else {
                return result;
            }
        } else {
            return super.hashCode();
        }
    }
}
