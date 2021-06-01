package com.algorithm;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 给一非空的单词列表，返回前 k 个出现次数最多的单词。
     *
     * 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率，按字母顺序排序。
     * https://leetcode-cn.com/problems/top-k-frequent-words/description/
     * @param words
     * @param k
     * @return
     */
    public List<String> topKFrequent(String[] words, int k) {
        class WordWithCount implements Comparable<WordWithCount>{
            public String getWord() {
                return word;
            }

            String word;
            int count;

            public WordWithCount(String word, int count) {
                this.word = word;
                this.count = count;
            }

            @Override
            public int compareTo(WordWithCount o) {
                if(this.count == o.count){
                    //次数相同按字典序排列
                    return o.word.compareTo(this.word);
                }
                return this.count-o.count;
            }
        }
        HashMap<String, WordWithCount> countHashMap = new HashMap<>();
        for(String word :words){
            WordWithCount wordWithCount = new WordWithCount(word,0);
            if(countHashMap.containsKey(word)){
                wordWithCount = countHashMap.get(word);
            }
            wordWithCount.count = wordWithCount.count+1;
            countHashMap.put(word,wordWithCount);
        }

        Queue<WordWithCount> pQueue = new PriorityQueue<WordWithCount>();
        for(WordWithCount wordWithCount : countHashMap.values()){
            if(pQueue.size()<k){
                pQueue.offer(wordWithCount);
                continue;
            }
            WordWithCount peek = pQueue.peek();
            if(wordWithCount.compareTo(peek)<0){
                continue;
            }
            pQueue.poll();
            pQueue.offer(wordWithCount);
        }
        LinkedList<String> result = new LinkedList<>();
        int len = pQueue.size();
        for(int i=0;i<len;i++){
            WordWithCount poll = pQueue.poll();
            result.addFirst(poll.word);
        }
        return result;
    }

    public static void main(String[] args) {

    }


}
