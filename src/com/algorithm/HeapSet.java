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

    /**
     * 给你一个整数数组 heights ，表示建筑物的高度。另有一些砖块 bricks 和梯子 ladders 。
     *
     * 你从建筑物 0 开始旅程，不断向后面的建筑物移动，期间可能会用到砖块或梯子。
     *
     * 当从建筑物 i 移动到建筑物 i+1（下标 从 0 开始 ）时：
     *
     * 如果当前建筑物的高度 大于或等于 下一建筑物的高度，则不需要梯子或砖块
     * 如果当前建筑的高度 小于 下一个建筑的高度，您可以使用 一架梯子 或 (h[i+1] - h[i]) 个砖块
     * 如果以最佳方式使用给定的梯子和砖块，返回你可以到达的最远建筑物的下标（下标 从 0 开始 ）。
     *
     * https://leetcode-cn.com/problems/furthest-building-you-can-reach
     * @param heights
     * @param bricks
     * @param ladders
     * @return
     */
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        //大顶堆
        Queue<Integer> heightQueue = new PriorityQueue<>(Comparator.reverseOrder());
        int index = 0;
        while(index<heights.length-1){
            int height = heights[index+1] - heights[index];
            //高度差大于0时，需要使用梯子或砖块
            if(height>0){
                heightQueue.offer(height);
                if(height>bricks){
                    //砖块不够，使用梯子，将梯子用在所有高度差中高度差最大的地方
                    //并返还之前该高度使用的砖块
                    if(ladders<=0){
                        //梯子不够，无法继续前进
                        break;
                    }else{
                        Integer poll = heightQueue.poll();
                        bricks = bricks+poll-height;
                        //使用梯子
                        ladders--;
                    }
                }else{
                    //砖块足够使用砖块
                    bricks = bricks -height;
                }
            }
            index++;
        }
        return index;
    }


    /**
     * 有一棵特殊的苹果树，一连 n 天，每天都可以长出若干个苹果。在第 i 天，树上会长出 apples[i] 个苹果，这些苹果将会在 days[i] 天后（也就是说，第 i + days[i] 天时）腐烂，变得无法食用。也可能有那么几天，树上不会长出新的苹果，此时用 apples[i] == 0 且 days[i] == 0 表示。
     * 你打算每天 最多 吃一个苹果来保证营养均衡。注意，你可以在这 n 天之后继续吃苹果。
     * 给你两个长度为 n 的整数数组 days 和 apples ，返回你可以吃掉的苹果的最大数目。
     *
     * 没法吃还没长出来的果子！
     *
     * https://leetcode-cn.com/problems/maximum-number-of-eaten-apples
     * @return
     */

    class Apples implements Comparable<Apples>{
        int nums;
        int expirDate;

        public Apples(int nums, int expirDate) {
            this.nums = nums;
            this.expirDate = expirDate;
        }
        @Override
        public int compareTo(Apples apples) {
            return this.expirDate-apples.expirDate;
        }
    }

    public int eatenApples(int[] apples, int[] days) {


        int appleEats = 0;
        //优先吃腐蚀日期早的苹果，故使用小顶堆
        Queue<Apples> appleQueue = new PriorityQueue<>(Comparator.comparingInt(v-> v.expirDate));
        for(int i=0;i<apples.length;i++){
            int nums = apples[i];
            int expirDate = i + days[i];
            if(nums!=0){
                Apples apple = new Apples(nums,expirDate);
                appleQueue.offer(apple);
            }
            if(eatApple(i, appleQueue)){
                appleEats++;
            }
        }
        int day = apples.length;
        while(!appleQueue.isEmpty()){
            if(eatApple(day,appleQueue)){
                appleEats++;
            }
            day++;
        }
        return appleEats;
    }

    public boolean eatApple(int day,Queue<Apples> appleQueue){
        while(!appleQueue.isEmpty()&&appleQueue.peek().expirDate<=day){
            appleQueue.poll();
        }
        if(appleQueue.isEmpty()){
            return Boolean.FALSE;
        }
        Apples peek = appleQueue.peek();
        peek.nums = peek.nums-1;
        if(peek.nums==0){
            appleQueue.poll();
        }
        return Boolean.TRUE;
    }


    /**
     * 汽车从起点出发驶向目的地，该目的地位于出发位置东面 target英里处。
     * 沿途有加油站，每个station[i]代表一个加油站，它位于出发位置东面station[i][0]英里处，并且有station[i][1]升汽油。
     * 假设汽车油箱的容量是无限的，其中最初有startFuel升燃料。它每行驶 1 英里就会用掉 1 升汽油。
     * 当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中。
     * 为了到达目的地，汽车所必要的最低加油次数是多少？如果无法到达目的地，则返回 -1 。
     * 注意：如果汽车到达加油站时剩余燃料为 0，它仍然可以在那里加油。如果汽车到达目的地时剩余燃料为 0，仍然认为它已经到达目的地。
     *
     * https://leetcode-cn.com/problems/minimum-number-of-refueling-stops
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        //将stations转换，下标为距离起点距离，值为该点含油量
        int staLen = stations.length;
        int cur = 0;
        int addFuelTimes = 0;
        int staIndex = 0;
        //构造大顶堆
        Queue<Integer> fuelQueue = new PriorityQueue<>(Comparator.reverseOrder());
        while(cur<target){
            if(staIndex< staLen&& stations[staIndex][0]==cur && stations[staIndex][1]>0){
                int fuel = stations[staIndex][1];
                fuelQueue.offer(fuel);
                staIndex++;
            }
            if(startFuel == 0){
                if(fuelQueue.isEmpty()){
                    break;
                }else{
                    Integer poll = fuelQueue.poll();
                    startFuel+=poll;
                    addFuelTimes++;
                }
            }
            if(staIndex<staLen&&startFuel>=stations[staIndex][0]-cur){
                startFuel-=(stations[staIndex][0]-cur);
                cur = stations[staIndex][0];
            }else {
                cur+=startFuel;
                startFuel = 0;
            }

        }

        return cur >= target?addFuelTimes:-1;
    }

    /**
     [2,1,1,4,5]
     [10,10,6,4,2]
     * @param args
     */
    public static void main(String[] args) {
        int target = 1000;
        int startFuel = 83;
        int[][] stations = {{47,220},{65,1},{98,113},{126,196},{186,218},{320,205},{686,317},{707,325},{754,104},{781,105}};
        int i = new HeapSet().minRefuelStops(target, startFuel,stations);
        System.out.println(i);
    }


}
