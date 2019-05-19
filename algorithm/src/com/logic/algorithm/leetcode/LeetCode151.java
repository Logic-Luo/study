package com.logic.algorithm.leetcode;

/**
 * 151. Reverse Words in a String
 *
 * @author logic
 * @date 2019/5/19 2:05 PM
 * @since 1.0
 */
public class LeetCode151 {
    public static void main(String[] args) {
        System.out.println(new Solution().reverseWords("a good   example"));
    }

    static class Solution1 {
        public String reverseWords(String s) {
            if (s == null || s.trim() == "") {
                return s;
            }
            String[] array = s.trim().split(" +");
            int i = 0;
            int j = array.length - 1;

            String temp = null;
            while (i < j) {
                temp = array[j];
                array[j] = array[i];
                array[i] = temp;
                j--;
                i++;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int k = 0; k < array.length; k++) {
                stringBuilder.append(array[k]);
                if (k != array.length - 1) {
                    stringBuilder.append(" ");
                }
            }
            return stringBuilder.toString();
        }
    }

    static class Solution {
        public String reverseWords(String s) {
            String res = "";
            String[] words = s.trim().split("\\s+");
            for (int i = words.length - 1; i > 0; --i) {
                res += words[i] + " ";
            }
            return res + words[0];
        }
    }
}
