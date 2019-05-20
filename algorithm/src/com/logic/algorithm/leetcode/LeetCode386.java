package com.logic.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 386. Lexicographical Numbers
 *
 * @author logic
 * @date 2019/5/20 3:26 PM
 * @since 1.0
 */
public class LeetCode386 {
    public static void main(String[] args) {
        List<Integer> integers = new Solution().lexicalOrder(34);
        integers.forEach(a -> System.out.println(a));
    }

    static class Solution {
        public List<Integer> lexicalOrder(int n) {
            List<Integer> resultList = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                dfs(resultList, i, n);
            }
            return resultList;
        }

        public void dfs(List<Integer> list, int start, int n) {
            if (start > n) {
                return;
            }
            list.add(start);
            for (int i = 0; i < 10; i++) {
                if (start * 10 + i > n) {
                    return;
                } else {
                    dfs(list, start * 10 + i, n);
                }
            }
        }
    }
}
