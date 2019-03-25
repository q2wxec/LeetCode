package com.algorithm.sort;

import com.algorithm.sort.common.CommonTools;
import com.algorithm.sort.common.Sort;

import java.util.Arrays;

/**
 * @Auther: FXX
 * @Date: 2019/1/23 15:27
 * @Description:
 */
public class InsertSort implements Sort {

    public static void main(String[] args) {
        boolean result = CommonTools.examSortAlgorithm(new InsertSort());
        System.out.println(result?"通过校验":"未通过校验");
    }

    public int[] insertSort(int[] ary){
        int[] copyAry = Arrays.copyOf(ary, ary.length);
        for(int i=0;i<copyAry.length-1;i++){
          for(int j=i+1;j>0;j--){
              if(copyAry[j]<copyAry[j-1]){
                  CommonTools.swap(copyAry,j,j-1);
              }else{
                  break;
              }
          }
        }
        return copyAry;
    }

    @Override
    public int[] sortArray(int[] array) {
        return new InsertSort().insertSort(array);
    }
}
