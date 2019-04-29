package com.ch1.base;


/**
 * @author sxylml
 * @Date : 2019/4/26 10:07
 * @Description:
 */
public class StartAndRun {
    private static class ThreadRun extends Thread {
        @Override
        public void run() {
            int i = 100;
            while (i > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I am " + Thread.currentThread().getName() + " and now the i=" + i--);
            }

        }
    }

    public static void main(String[] args) {
        ThreadRun threadRun = new ThreadRun();
        threadRun.setName("threadRun");
        // 结果当前线程是主线程，并不是threadRun
//        threadRun.run();
        threadRun.start();
    }
}
