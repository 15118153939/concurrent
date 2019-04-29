package com.ch1.base;

import java.util.concurrent.ExecutionException;

/**
 * @author sxylml
 * @Date : 2019/4/26 11:22
 * @Description: 守护线程
 */
public class DaemonThread {

    private static class UseThread extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    System.out.println(Thread.currentThread().getName()
                            + " I am extends Thread.");
                }
                System.out.println(Thread.currentThread().getName()
                        + " interrupt flag is " + isInterrupted());
            } finally {
                //守护线程中finally不一定起作用
                System.out.println(" .............finally");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        守护线程对内存的管理
        UseThread useThread = new UseThread();
        useThread.setDaemon(true);
        useThread.start();
        Thread.sleep(5);
        useThread.interrupt();
    }

}
