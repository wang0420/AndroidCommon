package com.app.leetcode;

import java.util.Arrays;

/**
 * https://www.iqiyi.com/v_19rrhzyejc.html#curid=244560500_e25e6b6292401934bc5b9b45ed241767
 * Created by wangwei on 2020/5/11.
 * 选择一个间隔d，将数列中下标间隔为d的元素划分到同一组
 * 对每一组做选择排序
 * 减小d，并重复第一、第二步操作，直到d=1
 * <p>
 * <p>
 * 通过一定的步长 把初始序列划分为多个子序列，让子序列先进性排序，
 * 然后选定一个更小的步长 重复上面动作
 * 当步长等于1  就是对整个序列排序
 * <p>
 * 分组的停止条件  就是分到步长的前一个元素
 *
 *
 * <p>
 * 时间复杂度
 * 最佳：O(n)
 * 平均：O(nlog n)
 * 最差：O(n^2)
 */

public class 排序_希尔 {
    public static void main(String[] args) {
        int[] numbers = {7, 5, 13, 2, 10};

        int div = numbers.length / 2;//取第一个间隔
        while (div > 0) {
            for (int i = div; i < numbers.length; i++) {//对每个区间做插入排序
                int current = numbers[i];
                int preindex = i - div;
                while ((preindex >= 0) && (numbers[preindex] > current)) {
                    numbers[preindex + div] = numbers[preindex];
                    preindex -= div;
                }
                numbers[preindex + div] = current;
            }
            div = div / 2;
        }
        System.out.println("wan" + Arrays.toString(numbers));


    }

}
