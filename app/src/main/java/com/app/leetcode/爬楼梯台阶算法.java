package com.app.leetcode;

/**
 * Created by wangwei on 2020/5/6.
 * 爬楼梯台阶算法
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶(最多爬2阶)。
 * 你有多少种不同的方法可以爬到楼顶呢？
 * 注意：给定 n 是一个正整数。
 * https://www.bilibili.com/video/BV1jK4y187g2/
 */

public class 爬楼梯台阶算法 {

    /**
     * F(n) 当n等于各个数字的时候
     * n=1   1
     * n=2   11  2
     * n=3   111  12  21
     * n=4   1111  112  121  211  22
     * 很明显符合斐波拉契数列规律：1、2、3、5、8、13、24......。
     * 第n个台阶的走法=第（n-1）个台阶的走法 + 第（n-2）个台阶的走法。
     */

    public static void main(String[] args) {
        System.out.println("--->" + getWays(10));
        System.out.println("--->" + calculate2(10));
        System.out.println("--->" + calculate3(2));


    }


    /**
     * 递归法 ,当台阶数稍微大一点的时候就算不出来了，甚至是发生爆栈StackOverflowError。
     * 而且有重复计算 标比喻f(6)=f(5)+f(4)  需要计算一次f(4)   当计算f(5)的时候有需要计算一次
     * 所以重复计算次数比较多
     * 因为递归本质上就是遍历二叉树，做了大量的重复计算。
     */
    private static int getWays(int n) {
        if (n == 1 || n == 2) {
            return n;
        } else {
            return getWays(n - 1) + getWays(n - 2);
        }
    }

    /**
     * 循环法
     * 利用了f（n） = f（n-1） + f（n-2），不过是通过遍历的方式来实现的，
     * 这里要计算出第1～n-1项各自的走法数，然后倒数第二项+倒数第三项就是最终的结果。
     * 用两个临时变量保存f（n-1） 和 f（n-2）的走法
     */
    private static int calculate2(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        int one = 1;
        int two = 2;
        int temp = 0;
        for (int i = 3; i <= n; i++) {
            temp = one + two;
            one = two;
            two = temp;
            System.out.println("one="+one);
            System.out.println("two="+two);
            System.out.println("temp="+temp);
        }
        return temp;
    }

    /**
     * 动态规划法
     * 把当前问题拆分成多个子问题，再合并子问题的解就可以得出当前问题的解；
     * 此外，一旦某个给定子问题的解已经算出，会被存储起来，下次需要的时候直接获取。
     * 动态规划三要素：
     * 1.最优子结构  f(n-1),f(n-2)
     * 2.边界   f(1) = 1 ,f(2) = 2
     * 3.状态转移公式 f(n) = f(n-1)+f(n-2)
     * <p>
     * 思路  用一个数组存储每一个台阶元素的走法
     */
    private static int calculate3(int n) {
        if (n == 1) {
            return n;
        }

        int[] array = new int[n + 1];//数组游标从0开始 所以+1
        array[0] = 1;
        array[1] = 1;
        for (int i = 2; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2];//当n==2的时候有 array[0] 所以定义 array[0] = 1;
        }
        return array[n];

    }
}
