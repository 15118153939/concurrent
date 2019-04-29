package com.ch1.base.safeend;

/**
 * @author sxylml
 * @Date : 2019/4/26 09:30
 * @Description: 实现接口Runnable的线程如何中断
 */
public class EndRunnable {
    private static class UseRunnable implements Runnable {

        @Override
        public void run() {
//            执行这段代码的哪个线程的标识位
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " I am implements Runnable. interrupt is :" +Thread.currentThread().isInterrupted() );
            }
            System.out.println(Thread.currentThread().getName() +" interrupt flag is "+Thread.currentThread().isInterrupted());

        }
    }
    public static void main(String[] args) throws InterruptedException {
        UseRunnable useRunnable = new UseRunnable();
        Thread endThread = new Thread(useRunnable,"endThread");
        endThread.start();
        Thread.sleep(20);
        endThread.interrupt();

        Thread endThread2 = new Thread(useRunnable,"endThread2");
        endThread2.start();
        Thread.sleep(20);
        endThread2.interrupt();
    }
}
