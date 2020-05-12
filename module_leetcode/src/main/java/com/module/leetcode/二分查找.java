package com.module.leetcode;

/**
 * Created by wangwei on 2020/5/9.
 * 在有序表中，取中间元素作为比较对象，若给定值与中间元素相等，则查找成功；若给定值小于中间元素，则在中间元素的左半区继续查找；若给定值大于中间元素，则在中间元素的右半区继续查找。不断重复上述过程，直到找到为止。
 * 从二分查找的定义我们可以看出，使用二分查找有两个前提条件：
 * （1）待查找的列表必须有序。
 * （2）必须使用线性表的顺序存储结构来存储数据。
 * <p>
 * 当序列为偶数的时候  向下取整
 * 比如8个数：1,2,3,4,5,6,7,8
 * mid=(0+7)/2=4
 */

public class 二分查找 {

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 6, 9, 11, 23};
        int target = 3;
        System.out.println("__" + test(array, target));
    }

    private static int test(int[] array, int target) {

        int left = 0;
        int right = array.length - 1;
        int middle = (left + right) / 2;

        while (true) {
            if (array[middle] == target) {
                return middle;
            } else if (array[middle] < target) {
                left = middle + 1;
            } else if (array[middle] > target) {
                right = middle - 1;
            }
            middle = (left + right) / 2;
        }


    }

    ;
}
