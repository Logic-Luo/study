package com.logic.algorithm.leetcode;

/**
 * 440. K-th Smallest in Lexicographical Order
 *
 * @author logic
 * @date 2019/5/19 6:08 PM
 * @since 1.0
 */
public class LeetCode440 {
    public static void main(String[] args) {
        System.out.println(new Solution().findKthNumber(13, 2));

    }
    static class Solution {
        public int findKthNumber(int n, int k) {
            int cur = 1;
            k = k -1;

            //整体思想就是找到一个，就把这个k的数量减一
            while (k > 0) {
                //开始节点
                long start = cur;
                //结束节点
                long end = cur + 1;
                //子节点的数目
                int childrenCount = 0;

                //计算first与last之间元素的个数
                while (start <= n) {
                    //用于计算开始于结束期间的所有元素的个数
                    childrenCount += Math.min(n + 1, end) - start;
                    //因为是十叉树，每次乘以10，开始和结束每次扩大10倍
                    start = start * 10;
                    end = end * 10;
                }

                //如果first与last之间元素的个数小于k个，则向后找
                if (childrenCount <= k) {
                    cur++;
                    k -= childrenCount;
                } else {
                    //如果first与last之间元素的个数大于k，则说明要找的元素在first的子节点中
                    cur *= 10;
                    k--;
                }
            }
            return cur;
        }
    }
}
