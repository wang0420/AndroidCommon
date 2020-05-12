package com.module.leetcode;

import java.util.Arrays;

/**
 * Created by wangwei on 2020/5/11.
 * 选取一个基准值，一般以头元素或尾元素
 * 以选取值为基准，将数列分成两个子数列，大于基准值的在右边，小于或等于基准值的在左边
 * 分别对基准值左右两边的的子序列重复第二步操作
 * 重复第二、第三步直到子序列中只有一个元素
 * <p>
 * 会做n次二分 一次为logn n次则就是O（nlogn）
 * 时间复杂度
 * 最佳：O(nlog n)
 */

public class 排序_快速 {
    public static void main(String[] args) {
        int[] arr = {7, 5, 13, 2, 10};
        sort(arr, 0, arr.length - 1);
        System.out.println("__" + Arrays.toString(arr));

    }

    public static void sort(int[] numbers,int left,int right){
        if(left>=right){
            return;
        }
        int tag = numbers[left];
        int lo = left;
        int hi = right;
        while (left<right){
            while ((tag<=numbers[right])&&(left<right)){
                right--;
            }
            numbers[left] = numbers[right];
            System.out.println("__" + Arrays.toString(numbers));
            while ((tag>=numbers[left])&&(left<right)){
                left++;
            }
            numbers[right] = numbers[left];

        }

        numbers[left] = tag;
        sort(numbers,lo,left-1);
        sort(numbers,left+1,hi);
    }

    public static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int index = getIndex(arr, start, end);//分区索引位
            System.out.println("__" +index);

            quickSort(arr, 0, index - 1);
            quickSort(arr, index + 1, end);
        }
    }

    //分区并返回索引值
    private static int getIndex(int[] arr, int start, int end) {
        //记录需要排序的下标
        int i = start;
        int j = end;
        //第一个坑位，基准值，
        int x = arr[i];
        //循环找出比基准值大的数和比基准值小的数
        while (i < j) {
            //先从右向左对比找出小于x的数,arr[j] >= x，不需要移动数据，则继续往前走
            while (i < j && arr[j] >= x) {
                j--;
            }
            //比基准值小的时候会跳出上面的循环，交换数字
            if (i < j) {
                //把找到的元素放入第一个坑位
                arr[i] = arr[j];
                i++;
            }

            //先从左向右对比找出大于x的数
            while (i < j && arr[i] < x) {
                i++;
            }
            if (i < j) {
                //把找到的元素放入第一个坑位
                arr[j] = arr[i];
                j--;
            }

        }

        arr[i] = x;
        return i;
    }


}
