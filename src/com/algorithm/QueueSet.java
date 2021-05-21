package com.algorithm;

import java.util.*;

public class QueueSet {

    class TreeNode {
        // 树结点中的元素值
        int val = 0;
        // 二叉树结点的左子结点
        TreeNode left = null;
        // 二叉树结点的右子结点
        TreeNode right = null;
    }

    class Node {
        // 树结点中的元素值
        int val = 0;
        // 二叉树结点的左子结点
        Node left = null;
        // 二叉树结点的右子结点
        Node right = null;

        Node next = null;
    }



    /**
     * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
     * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        int length = 0;
        Queue<TreeNode> list = new LinkedList<>();
        list.add(root);
        while(!list.isEmpty()){
            length = list.size();
            List<Integer> cur = new ArrayList<>();
            while(length>0){
                TreeNode poll = list.poll();
                cur.add(poll.val);
                if(poll.left!=null){
                    list.offer(poll.left);
                }
                if(poll.right!=null){
                    list.offer(poll.right);
                }
                length--;
            }
            result.add(cur);
        }
        return result;
    }

    /**
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     * https://leetcode-cn.com/problems/populating-next-right-pointers-in-each-node-ii/submissions/
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if(root == null){
            return null;
        }
        List<Node> cur = new ArrayList<>();
        cur.add(root);
        List<Node> next = null;
        while (!cur.isEmpty()){
            next = new ArrayList<>();
            for(int i =0 ;i<cur.size();i++){
                Node node = cur.get(i);
                if(i+1<cur.size()){
                    node.next = cur.get(i+1);
                }
                if(node.left!=null){
                    next.add(node.left);
                }
                if(node.right!=null){
                    next.add(node.right);
                }
            }
            cur = next;
            next = null;
        }
        return root;
    }

    /**
     * 滑动窗口最大值
     * https://leetcode-cn.com/problems/sliding-window-maximum/description/
     * 给你一个整数数组 nums，有一个大小为k的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k个数字。滑动窗口每次只向右移动一位。
     *
     * 返回滑动窗口中的最大值。
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        //区间最大值，使用递减队列，队首为最大值
        int[] result = new int[nums.length-k+1];
        LinkedList<Integer> queue = new LinkedList();
        int kIndex = 0;
        for(int i=0;i<nums.length;i++){
            //当queue为空时入队
            if(queue.isEmpty()){
                queue.addLast(i);
                continue;
            }
            //当入队元素少于k个时
            //入队，并保持单调
            if(i<k-1){
                while(!queue.isEmpty()&&nums[queue.peekLast()]<=nums[i]){
                    queue.pollLast();
                }
                queue.addLast(i);
                continue;
            }
            //当入队元素大于或等于k个时
            //判断队首与当前元素index是否满足k区间，不满足，则队首出队
            //入队并保持单调，记录队首值
            while(i-queue.peekFirst()+1>k){
                queue.pollFirst();
            }
            while(!queue.isEmpty()&&nums[queue.peekLast()]<=nums[i]){
                queue.pollLast();
            }
            queue.addLast(i);
            result[kIndex++] = nums[queue.peekFirst()];
        }
        return result;
    }
}
