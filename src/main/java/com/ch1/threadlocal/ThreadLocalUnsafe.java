package com.ch1.threadlocal;

import tools.SleepTools;

/**
 * @author sxylml
 * @Date : 2019/5/11 13:14
 * @Description: ThreadLocal的线程不安全演示
 */
public class ThreadLocalUnsafe implements Runnable {

    // 错误的方式
//    public static Number number = new Number(0);

    public  Number number = new Number(0);

    public static ThreadLocal<Number> value = new ThreadLocal<Number>() {
    };

    @Override
    public void run() {
        //每个线程计数加一
        number.setNum(number.getNum() + 1);
        //将其存储到ThreadLocal中
        value.set(number);
        SleepTools.ms(2);
        //输出num值
        System.out.println(Thread.currentThread().getName() + "=" + value.get().getNum());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadLocalUnsafe()).start();
        }
    }


    private static class Number {
        public Number(int num) {
            this.num = num;
        }

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Number [num=" + num + "]";
        }
    }


}
