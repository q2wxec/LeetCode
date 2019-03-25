package com.algorithm.sort;

import com.algorithm.sort.common.CommonTools;
import com.algorithm.sort.common.Sort;

import java.util.Arrays;

/**
 * @Auther: FXX
 * @Date: 2019/1/23 15:27
 * @Description:
 */
public class SelectSort implements Sort {

    public static void main(String[] args) {
        boolean result = CommonTools.examSortAlgorithm(new SelectSort());
        System.out.println(result?"通过校验":"未通过校验");
    }

    public int[] selectSort(int[] ary){
        int[] copyAry = Arrays.copyOf(ary, ary.length);
        for(int i=0;i<copyAry.length-1;i++){
          int minIndex = i;
          for(int j=i;j<copyAry.length;j++){
              if(copyAry[j]<copyAry[minIndex]){
                  minIndex = j;
              }
          }
            CommonTools.swap(copyAry,i,minIndex);
        }
        return copyAry;
    }

    @Override
    public int[] sortArray(int[] array) {
        return new SelectSort().selectSort(array);
    }
}
