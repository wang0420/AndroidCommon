package com.app.leetcode;

import java.util.Arrays;

/**
 * Created by wangwei on 2020/5/11.
 * 将一个记录插入到已排好序的序列中，从而得到一个新的有序序列
 * （将序列的第一个数据看成是一个有序的子序列，
 * 然后从第二个记录逐个向该有序的子序列进行有序的插入，直至整个序列有序）
 * <p>
 * 时间复杂度
 * 最佳：O(n)
 * 平均：O(n^2)
 * 最差：O(n^2)
 * https://www.cnblogs.com/asis/p/6798779.html
 */

public class 排序_插入 {
    public static void main(String[] args) {
        int[] numbers = {7, 5, 13, 2, 10};


        int pre;
        int current;
        // {7, 5, 13, 2, 10}
        //假定第一位已经排序，所以从number[1]开始，
        for (int i = 1; i < numbers.length; i++) {
            pre = i - 1;
            current = numbers[i];//记录待排序的数值
            while ((pre >= 0) && (numbers[pre] >= current)) {
                numbers[pre + 1] = numbers[pre];//将大于等于current的值往后挪一位
                pre--;
            }
            numbers[pre + 1] = current;
            System.out.println("__" + Arrays.toString(numbers));
        }
    }


}
