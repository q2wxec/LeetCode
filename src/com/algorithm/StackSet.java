package com.algorithm;

import java.util.HashMap;
import java.util.Stack;

/**
 * @author Fxx
 */
public class StackSet {

    /**
     * 1.模拟运行
     * 2.寻找规律
     * 3.匹配算法
     * 4.考虑边界
     */


    /**
     * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/description/
     * 柱状图中最大的矩形
     * 找一个子数组，使得
     * min*length最大
     *
     * 寻找第一个小于/大于的问题用单调栈
     */
    public int largestRectangleArea(int[] heights) {
        //由左开始的递增栈
        Stack<Integer> leftIncrease = new Stack();
        //由右开始的递增栈
        Stack<Integer> rightIncrease = new Stack();
        int[] leftIndex =new int[heights.length];
        int[] rightIndex =new int[heights.length];
        for(int left=0;left<heights.length;left++){
           //左侧递增栈处理
            while (!leftIncrease.isEmpty()&&heights[leftIncrease.peek()]>=heights[left]){
                leftIncrease.pop();
            }
            //记录当前下标元素，左侧第一个小于它的数的下标
            if(leftIncrease.isEmpty()){
                leftIndex[left] = -1;
            }else{
                leftIndex[left] = leftIncrease.peek();
            }
            leftIncrease.push(left);
            //右侧递增栈处理
            int right = heights.length-left-1;
            while (!rightIncrease.isEmpty()&&heights[rightIncrease.peek()]>=heights[right]){
                rightIncrease.pop();
            }
            if(rightIncrease.isEmpty()){
                rightIndex[right] = heights.length;
            }else{
                rightIndex[right] = rightIncrease.peek();
            }
            rightIncrease.push(right);
        }


        int max = 0;
        for(int i=0;i<heights.length;i++){
            //定宽
            /*for(int j=i+1;j<=heights.length;j++){
                int temp = largestRectangleArea(heights, i, j);
                if(max<temp){
                    max = temp;
                }
            }*/
            //定高
            //int temp = largestRectangleArea(heights, i);
            int temp = heights[i]*(rightIndex[i]-1-(leftIndex[i]+1)+1);

            if(max<temp){
                max = temp;
            }
        }
        return max;
    }

    public int largestRectangleArea(int[] heights,int start,int end) {
        int min = heights[start];
        for(int i=start;i<end;i++){
            if(heights[i]<min){
                min = heights[i];
            }
        }
        return (end-start)*min;
    }

    public int largestRectangleArea(int[] heights,int heightIndex){
        int left = heightIndex;
        int right = heightIndex;
        while(left-1>=0&&heights[left-1]>=heights[heightIndex]){
            left--;
        }

        while(right+1<heights.length&&heights[right+1]>=heights[heightIndex]){
            right++;
        }
        return (right-left+1)*heights[heightIndex];
    }



    /**
     *在水中有许多鱼，可以认为这些鱼停放在 x 轴上。再给定两个数组 Size，Dir，Size[i] 表示第 i 条鱼的大小，Dir[i] 表示鱼的方向 （0 表示向左游，1 表示向右游）。这两个数组分别表示鱼的大小和游动的方向，并且两个数组的长度相等。鱼的行为符合以下几个条件:
     * 所有的鱼都同时开始游动，每次按照鱼的方向，都游动一个单位距离；
     *
     * 当方向相对时，大鱼会吃掉小鱼；
     *
     * 鱼的大小都不一样。
     *
     * Size = [4, 2, 5, 3, 1], Dir = [1, 1, 0, 0, 0]
     *
     * 3
     *
     * 向右游则入栈
     *
     * 向左游依次查看栈顶，
     * 若为向右游，则
     * 当向右游大，则不出栈，继续遍历
     * 当向右游小，则继续出栈，
     * 若为向左游，则将当前元素压栈
     *
     */
    public int solution(int[] fishSize, int[] fishDirection) {
        int length = fishSize.length;
        Stack<Integer> stack = new Stack();
        int left = 0;
        int right = 1;
        int i=0;
        while (i<length){
            int curSize = fishSize[i];
            int curDirection = fishDirection[i];
            //当前向右，入栈，继续循环
            if(curDirection == right){
                stack.push(i++);
                continue;
            }
            //当前向左情况
            //栈为空，入栈，继续循环
            if(stack.isEmpty()){
                stack.push(i++);
                continue;
            }
            //栈顶为向左，入栈，继续循环
            Integer peek = stack.peek();
            if(fishDirection[peek]==left){
                stack.push(i++);
                continue;
            }
            //栈顶为向右情况
            //栈顶大，不入栈，继续循环
            if(fishSize[peek]>curSize){
                i++;
                continue;
            }
            //栈顶小，则出栈，继续当前元素比较
            stack.pop();
        }
        return stack.size();
    }

    /**
     * 给你两个 没有重复元素 的数组nums1 和nums2，其中nums1是nums2的子集。
     * 请你找出 nums1中每个元素在nums2中的下一个比其大的值。
     * nums1中数字x的下一个更大元素是指x在nums2中对应位置的右边的第一个比x大的元素。如果不存在，对应位置输出 -1 。
     * <p>
     * https://leetcode-cn.com/problems/next-greater-element-i
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            map.put(nums1[i], i);
        }
        Stack<Integer> stack = new Stack();
        //由右向左便利使用递减栈
        int cur = nums2.length - 1;
        while (cur >= 0) {
            while (!stack.empty() && nums2[cur] >= nums2[stack.peek()]) {
                stack.pop();
            }
            int temp = -1;
            if (!stack.empty()) {
                temp = nums2[stack.peek()];
            }
            if (map.containsKey(nums2[cur])) {
                result[map.get(nums2[cur])] = temp;
            }
            stack.push(cur);
            cur--;
        }
        return result;
    }

    /**
     * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），
     * 输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，
     * 这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
     * <p>
     * 链接：https://leetcode-cn.com/problems/next-greater-element-ii
     *
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        int cur = nums.length - 1;
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < 2; i++) {
            while (cur >= 0) {
                while (!stack.empty() && nums[cur] >= nums[stack.peek()]) {
                    stack.pop();
                }
                int temp = -1;
                if (!stack.empty()) {
                    temp = nums[stack.peek()];
                }
                result[cur] = temp;
                stack.push(cur);
                cur--;
            }
            cur = nums.length - 1;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] ary1 = {1, 2, 1};

        int[] ints = new StackSet().nextGreaterElements(ary1);
        System.out.println(ints);
    }


}
