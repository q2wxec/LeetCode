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
}
