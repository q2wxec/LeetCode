package com.algorithm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HeapSet {

    class Heap {
        int size;

        public int[] getAry() {
            return ary;
        }

        int[] ary;

        public Heap(int length) {
            this.size = 0;
            this.ary = new int[length];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == ary.length;
        }

        public int peek() {
            if (isEmpty()) {
                return -1;
            }
            return ary[0];
        }

        public int pop() {
            if (isEmpty()) {
                return -1;
            }
            int head = ary[0];
            ary[0] = ary[--size];
            int i = 0;
            while (i < size) {
                int right = 2 * i + 2;
                int left = 2 * i + 1;
                if (right < size && ary[right] > ary[i] && ary[right] > ary[left]) {
                    swap(ary, right, i);
                    i = right;
                } else if (left < size && ary[left] > ary[i]) {
                    swap(ary, left, i);
                    i = left;
                } else {
                    break;
                }
            }
            return head;
        }

        public void push(int num) {
            ary[size] = num;
            int i = size++;
            while (i > 0) {
                int parent = (i - 1) / 2;
                if (parent >= 0 && ary[parent] < ary[i]) {
                    swap(ary, parent, i);
                    i = parent;
                } else {
                    break;
                }
            }
        }


        private void swap(int[] ary, int from, int to) {
            int temp = ary[from];
            ary[from] = ary[to];
            ary[to] = temp;
        }
    }


    /**
     * https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/
     * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        Heap heap = new Heap(k);
        for (int i = 0; i < arr.length; i++) {
            if (!heap.isFull()) {
                heap.push(arr[i]);
                continue;
            }
            if (arr[i] >= heap.peek()) {
                continue;
            }
            heap.pop();
            heap.push(arr[i]);
        }
        return heap.getAry();
    }


    /**
     * https://leetcode-cn.com/problems/top-k-frequent-elements/description/
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        class NumWithCount implements Comparable<NumWithCount>{
            int num;
            int count;

            @Override
            public int compareTo(NumWithCount o) {
                return this.count-o.count;
            }
        }
        HashMap<Integer, NumWithCount> map = new HashMap<>();
        for(int i : nums){
            if(map.containsKey(i)){
                NumWithCount numWithCount = map.get(i);
                numWithCount.count = numWithCount.count+1;
            }else{
                NumWithCount numWithCount = new NumWithCount();
                numWithCount.num = i;
                numWithCount.count = 1;
                map.put(i,numWithCount);
            }
        }

        PriorityQueue<NumWithCount> priorityQueue = new PriorityQueue<>();
        for(Map.Entry<Integer, NumWithCount> entry : map.entrySet()){
            NumWithCount numWithCount = entry.getValue();
            if(priorityQueue.size()<k){
                priorityQueue.offer(numWithCount);
                continue;
            }
            NumWithCount peek = priorityQueue.peek();
            if(numWithCount.compareTo(peek)<0){
                continue;
            }
            priorityQueue.poll();
            priorityQueue.offer(numWithCount);
        }

        int[] result = new int[k];
        for(int i=0;i<k;i++){
            result[i] = priorityQueue.poll().num;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] ary = {1,1,1,2,2,3};
        int[] topKFrequent = new HeapSet().topKFrequent(ary, 2);
        System.out.println(topKFrequent);
    }


}
