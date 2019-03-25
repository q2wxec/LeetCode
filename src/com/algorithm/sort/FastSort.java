package com.algorithm.sort;

import com.algorithm.sort.common.CommonTools;
import com.algorithm.sort.common.Sort;

import java.util.Arrays;

/**
 * @Auther: FXX
 * @Date: 2019/1/23 15:27
 * @Description:
 */
public class FastSort implements Sort {

    public static void main(String[] args) {
        boolean result = CommonTools.examSortAlgorithm(new FastSort());
        System.out.println(result?"通过校验":"未通过校验");

    }

    public int[] fastSort(int[] ary,int start,int end){
        if(start >= end){
            return ary;
        }
        if(end-start == 1){
            if(ary[end]<ary[start]){
                CommonTools.swap(ary,end,start);
            }
            return ary;
        }
        int flag =  start+(int)((end-start)*Math.random());
        CommonTools.swap(ary,flag,start);
        flag = start;
        for(int i=start+1;i<=end;i++){
            if(ary[i]<ary[flag]){
                CommonTools.swap(ary,i,flag+1);
                CommonTools.swap(ary,flag,flag+1);
                flag = flag+1;
            }
        }
        this.fastSort(ary,start,flag-1);
        this.fastSort(ary,flag+1,end);
        return ary;
    }

    public int[] fastSort(int[] ary){
        int[] copyAry = Arrays.copyOf(ary, ary.length);
        return this.fastSort(copyAry, 0, ary.length - 1);
    }

    @Override
    public int[] sortArray(int[] array) {
        return new FastSort().fastSort(array);
    }
}
