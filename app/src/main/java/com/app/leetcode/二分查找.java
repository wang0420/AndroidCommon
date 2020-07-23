package com.app.leetcode;

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
        test();
    }




    private static void test() {
        // 需要查找的数字
        int targetNumb = 8;
        // 目标有序数组
        int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int middle = 0;
        int low = 0;
        int high = arr.length - 1;
        int isfind = 0;

        while (low <= high) {
            middle = (high + low) / 2;
            if (arr[middle] == targetNumb) {
                System.out.println(targetNumb + " 在数组中,下标值为: " + middle);
                isfind = 1;
                break;
            } else if (arr[middle] > targetNumb) {
                // 说明该数在low~middle之间
                high = middle - 1;
            } else {
                // 说明该数在middle~high之间
                low = middle + 1;
            }
        }
        if (isfind == 0) {
            System.out.println("数组不含 " + targetNumb);
        }
    }


}
