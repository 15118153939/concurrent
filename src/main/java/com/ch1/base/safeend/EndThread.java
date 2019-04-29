package com.ch1.base.safeend;

/**
 * @author sxylml
 * @Date : 2019/4/26 09:14
 * @Description: 如何安全中断线程
 */
public class EndThread {

    private static class UseThread extends Thread {
        UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
//            打印中断标识位
//            在调用线程 x.interrupt() 方法之前是false
            System.out.println(threadName + ": Before the while loop ： interrupt flag = " + isInterrupted());

//            使用isInterrupted() 的判断不会
            while (!isInterrupted()) {
//            while (!Thread.interrupted()) {
                System.out.println(threadName + " is running");
                System.out.println(threadName + "inner interrrupt flag =" + isInterrupted());
            }
//          1  使用isInterrupted() 的判断不会重置标识位打印true  推荐使用
//          2  使用 interrupted() 会重置标识位，打印false
            System.out.println(threadName + ": After the while loop ： interrupt flag = " + isInterrupted());

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread = new UseThread("endThread");
        endThread.start();
        Thread.sleep(20);
        endThread.interrupt();//中断线程，其实设置线程的标识位true
    }

}
