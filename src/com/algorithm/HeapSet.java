package com.algorithm;

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
                if (2 * i + 2 < size) {
                    if (ary[2 * i + 2] > ary[i] && ary[2 * i + 2] > ary[2 * i + 1]) {
                        int temp = ary[2 * i + 2];
                        ary[2 * i + 2] = ary[i];
                        ary[i] = temp;
                        i = 2 * i + 2;
                    } else if (ary[2 * i + 1] > ary[i]) {
                        int temp = ary[2 * i + 1];
                        ary[2 * i + 1] = ary[i];
                        ary[i] = temp;
                        i = 2 * i + 1;
                    } else {
                        break;
                    }
                } else if (2 * i + 1 < size) {
                    if (ary[2 * i + 1] > ary[i]) {
                        int temp = ary[2 * i + 1];
                        ary[2 * i + 1] = ary[i];
                        ary[i] = temp;
                        i = 2 * i + 1;
                    } else {
                        break;
                    }
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
                if ((i - 1) / 2 >= 0 && ary[(i - 1) / 2] < ary[i]) {
                    int temp = ary[(i - 1) / 2];
                    ary[(i - 1) / 2] = ary[i];
                    ary[i] = temp;
                    i = (i - 1) / 2;
                } else {
                    break;
                }
            }
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

    public static void main(String[] args) {
        int[] ary = {0, 1, 1, 1, 4, 5, 3, 7, 7, 8, 10, 2, 7, 8, 0, 5, 2, 16, 12, 1, 19, 15, 5, 18, 2, 2, 22, 15, 8, 22, 17, 6, 22, 6, 22, 26, 32, 8, 10, 11, 2, 26, 9, 12, 9, 7, 28, 33, 20, 7, 2, 17, 44, 3, 52, 27, 2, 23, 19, 56, 56, 58, 36, 31, 1, 19, 19, 6, 65, 49, 27, 63, 29, 1, 69, 47, 56, 61, 40, 43, 10, 71, 60, 66, 42, 44, 10, 12, 83, 69, 73, 2, 65, 93, 92, 47, 35, 39, 13, 75};
        new HeapSet().getLeastNumbers(ary, 75);
    }


}
