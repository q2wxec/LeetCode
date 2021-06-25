package com.algorithm;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
     * https://leetcode-cn.com/problems/remove-linked-list-elements/description/
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode fake = new ListNode();
        ListNode cur = fake;
        while(head!=null){
            if(head.val!=val){
                cur.next = head;
                cur = head;
            }
            head = head.next;
        }
        cur.next=null;
        return fake.next;
    }

    /**
     * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/description/
     * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。
     * 返回同样按升序排列的结果链表。
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode fake = new ListNode();
        fake.next = head;
        ListNode cur = head;
        head = head.next;
        while(head!=null){
            if(cur.val!=head.val){
                cur.next = head;
                cur = head;
            }
            head = head.next;
        }
        cur.next = null;
        return fake.next;
    }
}
