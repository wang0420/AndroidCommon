package com.app.leetcode;

import java.util.Arrays;

/**
 * Created by wangwei on 2020/5/11.
 * <p>
 * 将两个的有序数列合并成一个有序数列，我们称之为"归并"。
 * 将数列平均分成两段
 * 分别对两段数列进行归并排序
 * 将两段有序的数列合并
 * 就是递归然后合并。
 * 时间复杂度：O(nlog n)
 * 该算法采用经典的分治（divide-and-conquer）策略
 * https://www.cnblogs.com/chengxiao/p/6194356.html
 */

public class 排序_归并 {
    public static void main(String[] args) {
        int[] numbers = {7, 5, 13, 2, 10};
        sort(numbers, 0, numbers.length - 1);
    }

    public static int[] sort(int[] nums, int low, int high) {
        int mid = (low + high) / 2;
        System.out.println(low+"---"+high);
        if (low < high) {
            sort(nums, low, mid);// 左边
            sort(nums, mid + 1, high);     // 右边
            // 左右归并
            merge(nums, low, mid, high);
        }
        return nums;
    }

    /**
     * 待排序数组将数组中low到high位置的数进行排序@param low  待排的开始位置@param mid  待排中间位置@param high 待排结束位置
     */
    public static void merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = nums[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            nums[k2 + low] = temp[k2];
        }
        System.out.println("wan" + Arrays.toString(temp));
    }
}
