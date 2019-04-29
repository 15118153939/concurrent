package com.ch1.base;


/**
 * @author sxylml
 * @Date : 2019/4/26 10:24
 * @Description: 演示Join（）方法的使用
 * 模拟 屌丝打饭 ，让屌丝的女神插队打饭，只有等屌丝的女神打完饭，屌丝才能打饭。 如果女神的男朋友来了，并且屌丝的女神让她的男朋友插队打饭
 * 嘿嘿嘿。屌丝只能等前面插队的都打完饭咯
 */
public class UseJoin {

    static class Goddess implements Runnable {
//        表示女女神的男朋友咯
        private Thread thread;
        public Goddess() {
        }
        public Goddess(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            System.out.println("Goddess开始排队打饭.....");
            try {
                if (thread != null) {
                    thread.join();
                }
            } catch (InterruptedException e) {
            }
            try {
                Thread.sleep(2000);//休眠2秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()
                    + " Goddess打饭完成.");
        }
    }

    static class GoddessBoyfriend implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);//休眠2秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("GoddessBoyfriend开始排队打饭.....");
            System.out.println(Thread.currentThread().getName()
                    + " GoddessBoyfriend打饭完成.");
        }
    }


    public static void main(String[] args)  throws Exception  {
//        Thread diaosi = Thread.currentThread();
        GoddessBoyfriend goddessBoyfriend = new GoddessBoyfriend();
        Thread gbf = new Thread(goddessBoyfriend);
        Goddess goddess = new Goddess(gbf);
        //Goddess goddess = new Goddess();
        Thread g = new Thread(goddess);
        g.start();
        gbf.start();
        System.out.println("屌丝开始排队打饭.....");
//        可以注释join 看运行结果
        g.join();
        //让主线程休眠2秒
        Thread.sleep(2000);

        System.out.println(Thread.currentThread().getName() + " 屌丝打饭完成.");
    }


}
