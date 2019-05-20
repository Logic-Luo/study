package com.logic.algorithm.leetcode;

/**
 * 162. Find Peak Element
 *
 * @author logic
 * @date 2019/5/20 4:24 PM
 * @since 1.0
 */
public class LeetCode162 {
    public static void main(String[] args) {

    }

    class Solution {
        public int findPeakElement(int[] nums) {
            int l = 0;
            int r = nums.length - 1;

            while (l < r) {
                int mid = (l + r) / 2;

                if (nums[mid] > nums[mid + 1]) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            return l;
        }
    }
}
