package com.algorithm.sort.test;

import com.algorithm.sort.*;
import com.algorithm.sort.common.CommonTools;

/**
 * @Auther: FXX
 * @Date: 2019/1/24 18:04
 * @Description:
 */
public class SortCompare {
    public static void main(String[] args) {
        CommonTools.compareSortAlgorithm(new SystemSort(),new CombinSort(),new FastSort(),new ShellSort(),new InsertSort(),new BubbleSort());
    }
}
