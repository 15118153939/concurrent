package com.ch2.forkjoin.sort;

import com.ch2.forkjoin.sum.MakeArray;

import java.util.Arrays;

/**
 * @author sxylml
 * @Date : 2019/5/17 10:26
 * @Description: 归并排序
 */
public class MergeSort {

//    thresholds

    public static int[] sort(int[] array, int threshold) {

        //如果数组长度小于等于阈值就直接用简单插入排序
        if (array.length <= threshold) {
            return InsertionSort.sort(array);
        } else {
            //切分数组，然后递归调用
            int mid = array.length / 2;
            int[] left = Arrays.copyOfRange(array, 0, mid);
            int[] right = Arrays.copyOfRange(array, mid + 1, array.length);
            return merge(left, right);
        }
    }

    public static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];

        for (int index = 0, i = 0, j = 0; index < result.length; index++) {

            //左边数组已经取完，完全取右边数组的值即可
            if (i >= left.length) {
                result[index] = right[j++];
            } else if (j >= right.length) {
                //右边数组已经取完，完全取左边数组的值即可
                result[index] = left[i++];
            } else if (left[i] > right[j]) {
                //左边数组的元素值大于右边数组，取右边数组的值
                result[index] = right[j];
            } else {
                //右边数组的元素值大于左边数组，取左边数组的值
                result[index] = left[i++];
            }

        }
        return result;
    }
}
