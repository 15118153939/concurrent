package com.ch2.forkjoin.sum;

import java.util.Random;

/**
 * @author sxylml
 * @Date : 2019/5/17 10:13
 * @Description: 创建数据
 */
public class MakeArray {

    public static int[] makeArray(int arraySize) {
        if (arraySize <= 0) {
            arraySize = 0;
        }
        //new一个随机数发生器
        Random r = new Random();
        int[] result = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            //用随机数填充数组
            result[i] = r.nextInt(arraySize * 3);
        }
        return result;
    }


    public static void printArray(int[] array) {

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            System.out.print(",");
        }
        System.out.println();
    }

}
