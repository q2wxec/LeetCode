package com.algorithm.sort;

import com.algorithm.sort.common.CommonTools;
import com.algorithm.sort.common.Sort;

import java.util.Arrays;

/**
 * @Auther: FXX
 * @Date: 2019/1/23 15:27
 * @Description:
 */
public class ShellSort implements Sort {

    public static void main(String[] args) {
        boolean result = CommonTools.examSortAlgorithm(new ShellSort());
        System.out.println(result?"通过校验":"未通过校验");

    }

    public int[] shellSort(int[] ary){
        int[] copyAry = Arrays.copyOf(ary, ary.length);
        int gap = 1;
        while(gap<copyAry.length){
            gap = gap*3;
        }
        gap = gap/3;
        while(gap>0){
            for(int i=0;i<gap;i++){
                int cursor = i+gap;
                while(cursor<copyAry.length){
                    int j = cursor;
                    while(j-gap>=0&&copyAry[j]<copyAry[j-gap]){
                        CommonTools.swap(copyAry,j,j-gap);
                        j = j-gap;
                    }
                    cursor = cursor + gap;
                }
            }
            gap = gap/3;
        }
        return copyAry;
    }

    @Override
    public int[] sortArray(int[] array) {
        return new ShellSort().shellSort(array);
    }
}
