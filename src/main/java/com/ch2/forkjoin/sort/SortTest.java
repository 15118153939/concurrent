package com.ch2.forkjoin.sort;

import com.ch2.forkjoin.sum.MakeArray;

/**
 * @author sxylml
 * @Date : 2019/5/24 15:27
 * @Description:
 */
public class SortTest {
    public static void main(String[] args) {
        System.out.println("================归并排序================================");
        long start = System.currentTimeMillis();
        int arraySize = 100000;
        int[] array = MakeArray.makeArray(arraySize);
        array = MergeSort.sort(array, 10);
        System.out.println(" spend time:" + (System.currentTimeMillis() - start) + "ms");


        System.out.println("================直接插入排序============================");
        start = System.currentTimeMillis();
        array = MakeArray.makeArray(arraySize);
        array = InsertionSort.sort(array);
        System.out.println(" spend time:" + (System.currentTimeMillis() - start) + "ms");

        System.out.println("================冒泡插入排序============================");
        start = System.currentTimeMillis();
        array = MakeArray.makeArray(arraySize);
        array = BubbleSort.sort(array);
        System.out.println(" spend time:" + (System.currentTimeMillis() - start) + "ms");


    }
}
