package com.ch2.forkjoin.sort;

import com.ch2.forkjoin.sum.MakeArray;

/**
 * @author sxylml
 * @Date : 2019/5/17 10:45
 * @Description: 简单插入排序
 */
public class InsertionSort {
    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        //当前待排序数据，该元素之前的元素均已被排序过
        int currentValue;
        //在已被排序过数据中倒序寻找合适的位置，如果当前待排序数据比比较的元素要小，将比较的元素元素后移一位
        for (int i = 0; i < array.length - 1; i++) {
            //已被排序数据的索引
            int preIndex = i;
            currentValue = array[preIndex + 1];

            while (preIndex >= 0 && currentValue < array[preIndex]) {
                //将当前元素后移一位
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            //while循环结束时，说明已经找到了当前待排序数据的合适位置，插入
            array[preIndex + 1] = currentValue;
           //   System.out.println("第" + (i + 1) + "次排序");
            //  MakeArray.printArray(array);
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println("============================================");
        int[] array = MakeArray.makeArray(10);
        System.out.println("排序前：");
        MakeArray.printArray(array);
        array = InsertionSort.sort(array);
        System.out.println("排序后：");
        MakeArray.printArray(array);

    }


}
