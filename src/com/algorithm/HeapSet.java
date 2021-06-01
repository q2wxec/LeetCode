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

    /**
     * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
     * （这里，平面上两点之间的距离是欧几里德距离。）
     * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。

     * https://leetcode-cn.com/problems/k-closest-points-to-origin
     *
     * @param points
     * @param k
     * @return
     */
    public int[][] kClosest(int[][] points, int k) {
        class Point implements Comparable<Point>{
            int x;
            int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int sqrtDistance(){
                return (int)(Math.pow(this.x,2)+Math.pow(this.y,2));
            }

            @Override
            public int compareTo(Point points) {
                return this.sqrtDistance()-points.sqrtDistance();
            }
        }
        Queue<Point> pointsQueue = new PriorityQueue<Point>(Comparator.reverseOrder());
        for(int i=0;i<points.length;i++){
            Point p = new Point(points[i][0],points[i][1]);
            if(pointsQueue.size()<k){
                pointsQueue.offer(p);
                continue;
            }
            Point peek = pointsQueue.peek();
            if(p.compareTo(peek)>0){
                continue;
            }
            pointsQueue.poll();
            pointsQueue.offer(p);
        }
        int size = pointsQueue.size();
        int[][] result = new int[size][2];
        for(int i=0;i<size;i++){
            Point poll = pointsQueue.poll();
            result[i][0] = poll.x;
            result[i][1] = poll.y;
        }
        return result;
    }

     class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 给你一个链表数组，每个链表都已经按升序排列。
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     * https://leetcode-cn.com/problems/merge-k-sorted-lists/description/
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode();
        Queue<ListNode> q = new PriorityQueue<ListNode>(Comparator.comparingInt(v -> v.val));
        for(ListNode node:lists){
            if(node!=null){
                q.offer(node);
            }
        }
        ListNode cur = head;
        while(!q.isEmpty()){
            ListNode poll = q.poll();
            cur.next = poll;
            cur = poll;
            if(poll.next!=null){
                q.offer(poll.next);
            }
        }
        return head.next;
    }

    /**
     * 给定两个以升序排列的整形数组 nums1 和 nums2, 以及一个整数 k。
     * 定义一对值(u,v)，其中第一个元素来自nums1，第二个元素来自 nums2。
     * 找到和最小的 k 对数字(u1,v1), (u2,v2) ... (uk,vk)。
     * https://leetcode-cn.com/problems/find-k-pairs-with-smallest-sums
     * 
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        class ValuePair implements Comparable<ValuePair>{
            int v1;
            int v2;

            public ValuePair(int v1, int v2) {
                this.v1 = v1;
                this.v2 = v2;
            }

            @Override
            public int compareTo(ValuePair valuePair) {
                return this.v1+this.v2-valuePair.v1-valuePair.v2;
            }
        }
        Queue<ValuePair> valuePairQueue = new PriorityQueue<>(Comparator.reverseOrder());
        for(int i = 0;i<nums1.length;i++){
            for(int j=0;j<nums2.length;j++){
                int num1 = nums1[i];
                int num2 = nums2[j];
                ValuePair valuePair = new ValuePair(num1,num2);
                if(valuePairQueue.size()<k){
                    valuePairQueue.add(valuePair);
                    continue;
                }
                ValuePair peek = valuePairQueue.peek();
                if(valuePair.compareTo(peek)>0){
                    continue;
                }
                valuePairQueue.poll();
                valuePairQueue.offer(valuePair);
            }
        }
        List<List<Integer>> result = valuePairQueue.stream().map(v -> {
            List<Integer> list = new ArrayList<>();
            list.add(v.v1);
            list.add(v.v2);
            return list;
        }).collect(Collectors.toList());

        return result;
    }

    public static void main(String[] args) {
        int[][] points = {{3,3},{5,-1},{-2,4}};
        int[][] ints = new HeapSet().kClosest(points, 2);
        System.out.println(ints);
    }


}
