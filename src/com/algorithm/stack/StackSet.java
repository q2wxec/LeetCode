package com.algorithm.stack;

import java.util.Arrays;
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
     */
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> leftIncrease = new Stack();
        Stack<Integer> rightIncrease = new Stack();
        int[] leftIndex =new int[heights.length];
        int[] rightIndex =new int[heights.length];
        for(int left=0;left<heights.length;left++){
            int right = heights.length-left-1;
            while (!leftIncrease.isEmpty()&&heights[leftIncrease.peek()]>heights[left]){
                leftIncrease.pop();
            }
            if(leftIncrease.isEmpty()){
                leftIndex[left] = -1;
            }else{
                leftIndex[left] = leftIncrease.peek();
            }
            leftIncrease.push(left);
            while (!rightIncrease.isEmpty()&&heights[rightIncrease.peek()]>heights[right]){
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

    public static void main(String[] args) {
        int[] ary = {2,1,2};
        int i = new StackSet().largestRectangleArea(ary);
        System.out.println(i);
    }

}
