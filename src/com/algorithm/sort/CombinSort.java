package com.algorithm.sort;

import com.algorithm.sort.common.CommonTools;
import com.algorithm.sort.common.Sort;

import java.util.Arrays;

/**
 * @Auther: FXX
 * @Date: 2019/1/23 15:27
 * @Description:
 */
public class CombinSort implements Sort {

    public static void main(String[] args) {
        boolean result = CommonTools.examSortAlgorithm(new CombinSort());
        System.out.println(result?"通过校验":"未通过校验");

    }

    public int[] combinSort(int[] ary,int start,int end){
        if(start >= end){
            return ary;
        }
        if(end-start == 1){
            if(ary[end]<ary[start]){
                CommonTools.swap(ary,end,start);
            }
            return ary;
        }
        int flag =  start+(int)((end-start)/2);
        this.combinSort(ary,start,flag);
        this.combinSort(ary,flag+1,end);
        int[] temp = new int[end-start+1];
        int cursor1 = start;
        int cursor2 = flag+1;
        int cursor3 = 0;
        while(cursor1<=flag&&cursor2<=end){
            if(ary[cursor1]<ary[cursor2]){
                temp[cursor3] = ary[cursor1];
                cursor1++;
            }else{
                temp[cursor3] = ary[cursor2];
                cursor2++ ;
            }
            cursor3++;
        }
        while(cursor1<=flag){
            temp[cursor3] = ary[cursor1];
            cursor1++;
            cursor3++;
        }
        while(cursor2<=end){
            temp[cursor3] = ary[cursor2];
            cursor2++;
            cursor3++;
        }
        for(int i=0;i<temp.length;i++){
            ary[start+i] = temp[i];
        }
        return ary;
    }

    public int[] combinSort(int[] ary){
        int[] copyAry = Arrays.copyOf(ary, ary.length);
        return this.combinSort(copyAry, 0, ary.length - 1);
    }

    @Override
    public int[] sortArray(int[] array) {
        return new CombinSort().combinSort(array);
    }
}
