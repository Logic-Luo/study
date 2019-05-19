package com.logic.algorithm.leetcode;


/**
 * 2. Add Two Numbers
 *
 * @author logic
 * @date 2019/5/19 3:14 PM
 * @since 1.0
 */
public class LeetCode2 {
    public static void main(String[] args) {
        ListNode node2 = new ListNode(2);
        ListNode node4 = new ListNode(4);
        ListNode node3 = new ListNode(3);
        node2.next = node4;
        node4.next = node3;

        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        ListNode node4_1 = new ListNode(4);
        node5.next = node6;
        node6.next = node4_1;


        System.out.println(new Solution().addTwoNumbers(node2, node5));


    }


}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode p = null;
        int sum = 0;
        int carry = 0;
        int mod = 0;
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;

            sum = x + y;
            carry = sum / 10;
            mod = sum % 10;

            ListNode node = new ListNode(mod);
            if (p == null) {
                p = node;
            } else {
                p.next = node;
                p = node;
            }
            if (head == null) {
                head = p;
            }
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }

        if (carry != 0) {
            ListNode node = new ListNode(carry);
            p.next = node;
        }

        return head;
    }
}