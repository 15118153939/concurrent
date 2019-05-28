package com.ch2.forkjoin.sort;

/**
 * @author sxylml
 * @Date : 2019/5/18 10:56
 * @Description: 冒泡排序
 */
public class BubbleSort {

    public static int[] sort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            //-1为了防止溢出
            for (int j = 0; j < array.length - i - 1; j++) {

                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }
}
