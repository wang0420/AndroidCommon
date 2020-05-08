package com.module.leetcode;

/**
 * 翻转字符串“wangwei”
 * 用两个指针，一个指向字符串的第一个字符 w，一个指向它的最后一个字符 i，
 * 然后互相交换。交换之后，两个指针向中央一步步地靠拢并相互交换字符，
 * 直到两个指针相遇。这是一种比较快速和直观的方法。
 * 由于无法直接修改字符串里的字符，所以必须先把字符串变换为数组，然后再运用这个算法
 */

public class 翻转字符串 {
    public static void main(String[] args) {
        System.out.println("---翻转字符串>");

        String str = "wangwei";
        char[] array = str.toCharArray();
        //左右指针
        int left = 0;
        int right = str.length() - 1;

        char temp;
        while (left < right && left != right) {
            temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
            System.out.println(left + "---" + right);

        }
        for (char a : array) {
            System.out.println(a);
        }


    }
}
