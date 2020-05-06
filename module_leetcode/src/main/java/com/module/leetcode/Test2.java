package com.module.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangwei on 2020/5/6.
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标
 */

public class Test2 {


    public static void main(String[] args) {
        int[] array = {1, 3, 2, 5, 7, 9, 11, 34, 6};
        System.out.println(twoSum(array, 9));


    }

    //方法一利用两层循环（不做具体描述）

    //方法二利用哈希表
    private static int[] twoSum(int[] nums, int target) {
        //创建一个map
        Map<Integer, Integer> map = new HashMap<>();

        //检查每个元素所对应的目标元素（target - nums[i]）是否存在于表中
        //用targer-nums[i]   计算那个数字能跟当前的数字相加等于target
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                System.out.println(i);
                System.out.println(map.get(complement));
            } else {
                map.put(nums[i], i);
            }

        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**方法三  指针
     * 1.数组排序
     * 2 .前后两个指针
     * 3.移动前后指针  当两个指针的和大于目标数字的时候  后面的指针向前移动  反之前面的指针向后移动
     * */

}
