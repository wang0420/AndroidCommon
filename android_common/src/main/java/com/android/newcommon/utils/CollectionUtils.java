package com.android.newcommon.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wangwei
 * @date 2020/7/23.
 */
public class CollectionUtils {
    private CollectionUtils() {
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static int getSize(Collection collection) {
        return collection == null ? 0 : collection.size();
    }

    public static boolean isPositionValid(Collection collection, int position) {
        return !isEmpty(collection) && (position >= 0) && (position < collection.size());
    }

    public static boolean isContainsSameData(List list1, List list2) {
        if (list1 == null && list1 == list2) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() == list2.size()) {
            for (int i = 0, size1 = list1.size(); i < size1; i++) {
                if (list1.get(i) != list2.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}

