package com.ch3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sxylml
 * @Date : 2019/5/27 15:34
 * @Description:
 */
public class UseAtomicInteger {
    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {


        System.out.println(ai.getAndIncrement());
        System.out.println(ai.incrementAndGet());
        //ai.compareAndSet();
        System.out.println(ai.addAndGet(24));

    }
}
