package com.logic.algorithm.leetcode;

/**
 * 206. Reverse Linked List
 *
 * @author logic
 * @date 2019/5/19 3:47 PM
 * @since 1.0
 */
public class LeetCode206 {
    class Solution {
        public ListNode reverseList(ListNode head) {
            ListNode p = head;
            head = null;
            while (p != null) {
                ListNode q = p;
                p = p.next;

                q.next = head;
                head = q;
            }
            return head;
        }
    }
}

