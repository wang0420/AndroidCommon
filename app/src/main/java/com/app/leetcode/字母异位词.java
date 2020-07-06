package com.app.leetcode;

import java.util.Arrays;

/**
 * Created by wangwei on 2020/5/7.
 * 字母异位词，也就是两个字符串中的相同字符的数量要对应相等
 * 例如，s 等于 “anagram”，t 等于 “nagaram”，s 和 t 就互为字母异位词。
 * 因为它们都包含有三个字符 a，一个字符 g，一个字符 m，一个字符 n，以及一个字符 r。
 * 解题思路
 * 可以利用两个长度都为 26 的字符数组来统计每个字符串中小写字母出现的次数，然后再对比是否相等；
 * <p>
 * 可以只利用一个长度为 26 的字符数组，将出现在字符串
 * s 里的字符个数加 1，而出现在字符串 t 里的字符个数减 1，
 * 最后判断每个小写字母的个数是否都为 0。
 */

public class 字母异位词 {
    public static void main(String[] args) {
        System.out.println("---字母异位词>"+isAnagram("abde","edbe"));

    }

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] counter = new int[26];
        for (int i = 0; i < s.length(); i++) {
            //数字的ASCII   a-z：97-122，
            counter[s.charAt(i) - 'a']++;
            counter[t.charAt(i) - 'a']--;
        }

        for (int count : counter) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    //数组排列的方法为 Arrays.sort(s)，
    // 转化数组的方法为 toCharArray(s);
    // 数组的比较方法为Arrays.equals(s,t);

    public static boolean isAnagram1(String s, String t) {

        char[] ss = s.toCharArray();//将s字符串转换为数组
        char[] tt = t.toCharArray();//将t字符串转换为数组

        if (ss.length != tt.length) return false;//长度不等，直接pass

        Arrays.sort(ss);//排列组合
        Arrays.sort(tt);

        return Arrays.equals(ss, tt);//看他们各自拼接的组合有无相等的可能

    }


}
