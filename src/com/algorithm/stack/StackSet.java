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
        int max = 0;
        for(int i=0;i<heights.length;i++){
            for(int j=i+1;j<=heights.length;j++){
                int temp = largestRectangleArea(heights, i, j);
                if(max<temp){
                    max = temp;
                }
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

    }

}
