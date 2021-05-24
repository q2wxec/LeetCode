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

    class NNode {
        public int val;
        public List<NNode> children;

        public NNode() {}

        public NNode(int _val) {
            val = _val;
        }

        public NNode(int _val, List<NNode> _children) {
            val = _val;
            children = _children;
        }
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
            //当入队元素少于k个时
            //入队，并保持单调
            //当入队元素大于或等于k个时
            //判断队首与当前元素index是否满足k区间，不满足，则队首出队
            //入队并保持单调，记录队首值
            while(!queue.isEmpty()&&i-queue.peekFirst()+1>k){
                queue.pollFirst();
            }
            while(!queue.isEmpty()&&nums[queue.peekLast()]<=nums[i]){
                queue.pollLast();
            }
            queue.addLast(i);
            if(i>=k-1){
                result[kIndex++] = nums[queue.peekFirst()];
            }

        }
        return result;
    }

    /**
     * https://leetcode-cn.com/problems/average-of-levels-in-binary-tree/description/
     * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组。
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        List<TreeNode> cur = new ArrayList<>();
        cur.add(root);
        while(!cur.isEmpty()){
            List<TreeNode> next = new ArrayList<>();
            double sum = 0;
            for(int i=0;i<cur.size();i++){
                TreeNode temp = cur.get(i);
                if(temp.left!=null){
                    next.add(temp.left);
                }
                if(temp.right!=null){
                    next.add(temp.right);
                }
                sum+=temp.val;
            }
            result.add(sum/cur.size());
            cur = next;
        }
        return result;
    }

    /**
     * https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/description/
     * 给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。
     *
     * 树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(NNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        Queue<NNode> queue = new LinkedList();
        queue.offer(root);
        int length = 1;
        while(!queue.isEmpty()){
            List<Integer> temp = new ArrayList<>();
            while(length>0){
                NNode poll = queue.poll();
                temp.add(poll.val);
                if(poll.children!=null){
                    for(NNode n : poll.children){
                        if(n!=null){
                            queue.offer(n);
                        }
                    }
                }
                length--;
            }
            result.add(temp);
            length = queue.size();
        }
        return result;
    }


    public static void main(String[] args) {
        int[] array = {1,3,-1,-3,5,3,6,7};
        new QueueSet().maxSlidingWindow(array,3);
    }
}
