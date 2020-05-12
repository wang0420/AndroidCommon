package com.module.leetcode;

import java.util.Arrays;

/**
 * Created by wangwei on 2020/5/12.
 * * 两个有序数组  找第k小
 * 方案一   合并遍历
 * 方案二：游标计数
 * 题目只要求第k大的数，没必要花力气将数组全部再排序，
 * 可以定义两个游标分别指向两个有序数组，按序移动，比较两个游标对应的值，小的值游标往后移动一位
 * ，并用count计数
 * 当count等于k时，返回两个游标指向的数中最小的那一个
 */

public class 两个有序数组找第K小 {
    public static void main(String[] args) {

        int K = 3;
        int[] array1 = {3, 6,  7};
        int[] array2 = {2,  8, 9};

        int i = 0;
        int j = 0;
        int count = 0;
        while (count < K - 1) {
            if (array1[i] <= array2[j]) {
                i++;
            } else {
                j++;
            }
            count++;
        }
        int ha = array1[i] >= array2[j] ? array2[j] : array1[i];
        System.out.println("wan" + ha);


    }
}
