package com.algorithm.sort;

import com.algorithm.sort.common.Sort;

import java.util.Arrays;

/**
 * @Auther: FXX
 * @Date: 2019/1/24 16:15
 * @Description:
 */
public class SystemSort implements Sort {
    @Override
    public int[] sortArray(int[] array) {
        return new SystemSort().systemSort(array);
    }

    public int[] systemSort(int[] ary){
        int[] copyAry = Arrays.copyOf(ary, ary.length);
        Arrays.sort(copyAry);
        return copyAry;
    }
}
