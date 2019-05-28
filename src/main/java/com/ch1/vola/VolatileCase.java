package com.ch1.vola;

import tools.SleepTools;

/**
 * @author sxylml
 * @Date : 2019/5/11 13:43
 * @Description: 演示Volatile的提供的可见性
 */
public class VolatileCase {
//        private static boolean ready;
    private volatile static boolean ready;
    private static int number;

    private static class PrintThread extends Thread {
        @Override
        public void run() {
            System.out.println("PrintThread is running.......");
            //无限循环 通过主线程修改ready 的值  看这子线程能感知到变化跳出循环不
            while (!ready) {
            }
            System.out.println("number = " + number);

        }
    }

    public static void main(String[] args) {
        new PrintThread().start();
        SleepTools.second(1);
        number = 51;
        ready = true;
        SleepTools.second(5);
        System.out.println("main is ended!");
    }

}
