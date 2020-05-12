package com.module.leetcode;

import java.util.Arrays;

/**
 * Created by wangwei on 2020/5/11.
 * 选择排序的基本思想是遍历数组的过程中，以 i 代表当前需要排序的序号，
 * 则需要在剩余的 [i…n-1] 中找出其中的最小值，然后将找到的最小值与 i 指向的值进行交换。
 * 因为每一趟确定元素的过程中都会有一个选择最大值的子流程，
 * 所以人们形象地称之为选择排序。选择排序的时间复杂度和空间复杂度分别为 O(n2 ) 和 O(1) 。
 * <p>
 * 原理：每一趟从待排序的记录中选出最小的元素，顺序放在已排好序的序列最后，直到全部记录排序完毕。
 */

public class 排序_选择 {
    public static void main(String[] args) {
        int[] arr = {10, 13, 15, 16, 9, 11, 23};
        //选择排序的优化
        for (int i = 0; i < arr.length; i++) {// 做第i趟排序
            int miniPos = i;
            for (int m = i + 1; m < arr.length; m++) {
                if (arr[miniPos] > arr[m]) {
                    miniPos = m;// 选最小的记录位置
                }
            }
            if (arr[i] > arr[miniPos]) {
                int temp = arr[i];
                arr[i] = arr[miniPos];
                arr[miniPos] = temp;
            }
            System.out.println("__" + Arrays.toString(arr));
        }

    }
}
