package com.ch3;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author sxylml
 * @Date : 2019/5/27 15:52
 * @Description:
 */
public class AtomicArray {
    static int[] value = new int[]{1, 2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        ai.getAndSet(0, 3);
        System.out.println(ai.get(0));
        //原数组不会变化
        System.out.println(value[0]);
    }
}
