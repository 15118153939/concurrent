package com.ch1.base;

import java.util.concurrent.ExecutionException;
/**
 * @author sxylml
 * @Date : 2019/4/25 17:26
 * @Description:
 */
public class NewThread {

    /**扩展自Thread类*/
    public static class UseThread extends Thread{

        @Override
        public void run() {
            super.run();
            // do my work;
            System.out.println("I am extend Thread");
        }
    }

    /**
     * 实现 Runnable 接口
     */
    public static class UseRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("I am implements Runnable");
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        UseThread useThread = new UseThread();
        useThread.start();
        // 连续调用2次以上start 方法会报错   Exception in thread "main" java.lang.IllegalThreadStateException
        useThread.start();
        UseRunnable useRunnable = new UseRunnable();
        new Thread(useRunnable).start();
    }
}
