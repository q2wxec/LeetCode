package com.algorithm.sort.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * @Auther: FXX
 * @Date: 2019/1/23 15:29
 * @Description:
 */
public class CommonTools {

    public static Random random = new Random();
    public static int retryCount = 1000;
    public static int sizeBound = 1000;

    public static boolean examSortAlgorithm(Sort sort){
        for(int i=0;i<retryCount;i++){
            int size = Math.abs(random.nextInt(sizeBound));
            int[] origin = CommonTools.genRandomIntArray(size);
            long start = System.nanoTime();
            int[] result = sort.sortArray(origin);
            long end = System.nanoTime();
            double timeSpend = (double)(end-start)/1000000d;
            System.out.println("class:"+sort.getClass()+"---size:"+size+"---time:"+timeSpend+"ms");
            int[] copyAry = Arrays.copyOf(origin, origin.length);
            Arrays.sort(copyAry);
            if(!compareIntArray(result,copyAry)){
                System.out.println(Arrays.toString(result));
                System.out.println(Arrays.toString(copyAry));
                System.out.println(Arrays.toString(origin));
                return false;
            }
        }
        return true;
    }

    public static void compareSortAlgorithm(Sort... sorts){
        HashMap<Class,Double> hashMap = new HashMap<Class,Double>();
        for(Sort sort : sorts){
            hashMap.put(sort.getClass(),0d);
        }
        for(int i=0;i<retryCount;i++){
            int size = Math.abs(random.nextInt(sizeBound));
            int[] origin = CommonTools.genRandomIntArray(size);
            for(Sort sort : sorts){
                long start = System.nanoTime();
                int[] result = sort.sortArray(origin);
                long end = System.nanoTime();
                double timeSpend = (double)(end-start)/1000000d;
                hashMap.put(sort.getClass(),hashMap.get(sort.getClass())+timeSpend);
            }
        }
        for(Sort sort : sorts){
            System.out.println(sort.getClass()+"总耗时："+hashMap.get(sort.getClass())+"ms");
        }

    }

    public static int[] genRandomIntArray(int size){
        int[] ary = new int[size];
        for(int i=0;i<size;i++){
            ary[i] = random.nextInt();
        }
        return ary;
    }

    public static boolean compareIntArray(int[] ary1,int[] ary2){
        if(ary1==null||ary2==null||ary1.length!=ary2.length){
            return ary1==ary2;
        }
        for(int i=0;i<ary1.length;i++){
            if(ary1[i]!=ary2[i]){
                return false;
            }
        }
        return true;
    }

    public static void swap(int[] ary,int i,int j){
        int temp = ary[i];
        ary[i] = ary[j];
        ary[j] = temp;
    }




}
