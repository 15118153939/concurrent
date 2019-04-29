package com.ch1.base.safeend;

/**
 * @author sxylml
 * @Date : 2019/4/26 09:41
 * @Description: 阻塞方法中抛出InterruptedException异常后，如果需要继续中断，需要手动再中断一次
 */
public class HasInterrputException {
    private static class UseThread extends Thread {

        public UseThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
//                    如果sleep,wait 等方法如果直接中断线程，哪资源等没执行完就中断了，和stop 情况类似了。 所以会重置标识位置 false
                    System.out.println(Thread.currentThread().getName() + " in InterruptedException interrupt flag is " + isInterrupted());
                    //资源释放后，再次执行中断一次
                    interrupt();
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " I am extends Thread."+isInterrupted());
            }
            System.out.println(Thread.currentThread().getName() +" interrupt flag is "+isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread endThread = new UseThread("HasInterrputEx");
        endThread.start();
        Thread.sleep(500);
        endThread.interrupt();


    }
}
