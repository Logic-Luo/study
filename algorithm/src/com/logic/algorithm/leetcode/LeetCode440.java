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

    }
    class Solution {
        public int findKthNumber(int n, int k) {
            int cur = 1;
            k = k-1;
            while (k > 0) {
                int steps = getSteps(n ,cur, cur + 1);
                if (steps <= k) {
                    cur += 1;
                    k -= steps;
                } else {
                    cur *= 10;
                    k -= 1;
                }
            }
            return cur;
        }

        public int getSteps(int n, long n1, long n2) {
            int steps = 0;
            while (n1 <= n) {
                steps += Math.min(n +1, n2) - n1;
                n1 *= 10;
                n2 *= 10;
            }
            return steps;
        }
    }
}
