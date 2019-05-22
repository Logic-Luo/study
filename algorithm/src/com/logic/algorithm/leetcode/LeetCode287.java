package com.logic.algorithm.leetcode;

/**
 * 287. Find the Duplicate Number
 *
 * @author logic
 * @date 2019/5/22 2:51 PM
 * @since 1.0
 */
public class LeetCode287 {
    class Solution {
        public int findDuplicate(int[] nums) {
            if (nums == null) {
                return 0;
            }
            int result = 0;
            for (int i = 0; i < nums.length; i++) {
                result = result ^ nums[i];
            }

            return result;
        }
    }
}
