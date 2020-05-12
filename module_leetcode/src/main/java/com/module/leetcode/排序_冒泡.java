package com.module.leetcode;

import java.util.Arrays;

/**
 * Created by wangwei on 2020/5/9.
 * 采用两两比较并交换位置的思路，就仿佛泡泡一样，较大的元素会慢慢“上浮”1从数列一端开始
 * ，比较相邻两个元素，如果第一个元素比第二个元素大，交换两个元素位置然后比较第2个数和第3个数，
 * 将小数放前，大数放后，如此继续，2移动至下一对相邻元素，再次进行比较和交换，
 * 直到数列最后一个元素3保证倒数第一的元素是数列中最大的）4不断重复以上操作。每次重复的时候，需要比较的元素个数都减1
 * 冒泡排序总的平均时间复杂度为：O(n2) ,时间复杂度和数据状况无关
 * <p>
 * <p>
 * 优化一
 * 假设我们现在排序ar[]={1,2,3,4,5,6,7,8,10,9}这组数据，按照上面的排序方式，
 * 第一趟排序后将10和9交换已经有序，接下来的8趟排序就是多余的，什么也没做。所以我们可以在交换的地方加一个标记，
 * 如果那一趟排序没有交换元素，说明这组数据已经有序，不用再继续下去。
 * <p>
 * 优化二
 * 既我们可以记下最后一次交换的位置，后边没有交换，必然是有序的，然后下一次排序从第一个比较到上次记录的位置结束即可。
 * https://blog.csdn.net/hansionz/article/details/80822494
 */

public class 排序_冒泡 {

    public static void main(String[] args) {
        int[] array = {1, 12, 3, 5, 6, 88};

        test(array);
        System.out.println(Arrays.toString(array));
    }


    private static void test(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int flag = 0;
            for (int j = 0; j < array.length - 1 - i; j++) {//每一轮都筛选出最大的数字  说循环次数-i
                if (array[j] > array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                    flag = 1;//加入标记
                }

            }
            //优化  减少循环次数  如果没有交换过元素，则已经有序
            if (flag == 0) {
                return;
            }
        }
    }
}
