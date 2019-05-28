package com.ch1.wn;

import tools.SleepTools;

/**
 * @author sxylml
 * @Date : 2019/5/14 15:58
 * @Description: 采用多线程技术，例如wait/notify，设计实现一个符合生产者和消费者问题的程序，
 * 对某一个对象（枪膛）进行操作，其最大容量是20颗子弹，
 * 生产者线程是一个压入线程，它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 */
public class bulletDemo {
    public static void main(String[] args) {

        Factory factory = new Factory();

        ParessIn paressIn = new ParessIn(factory);
        Shooting shooting = new Shooting(factory);

        new Thread(paressIn, "装弹线程").start();
        new Thread(shooting, "射击线程1").start();
//        new Thread(shooting, "射击线程2").start();


    }

    static class ParessIn implements Runnable {
        Factory factory;

        public ParessIn(Factory factory) {
            this.factory = factory;
        }

        @Override
        public void run() {

            while (true) {
                factory.pressIn();
                SleepTools.ms(200);
            }
        }
    }

    static class Shooting implements Runnable {
        Factory factory;

        public Shooting(Factory factory) {
            this.factory = factory;
        }

        @Override
        public void run() {
            while (true) {
                factory.shooting();
                SleepTools.ms(500);
            }
        }
    }


    static class Factory {
        //        子弹最大数
        public final static int MAX_SIZE = 20;
        //当前数量
        private int bulletSize;

        public Factory() {
        }

        /**
         * 压入子弹
         */
        public synchronized void pressIn() {
            String name = Thread.currentThread().getName();
            while (bulletSize >= MAX_SIZE) {
                try {
                    wait();
                    System.out.println("[" + name + " ]弹夹满了" + bulletSize);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            bulletSize++;
            System.out.println("[" + name + " ]压入子弹当前子弹数：" + bulletSize);
//          装入子弹，可以通知枪可以射击了
            notifyAll();
        }

        /**
         * 射击
         */
        public synchronized void shooting() {

            String name = Thread.currentThread().getName();
            while (bulletSize <= 0) {
                try {
//                    没有子弹等待
                    System.out.println("[" + name + "] 弹夹为空，不能射击，等待子弹!" + bulletSize);
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            bulletSize--;
            System.out.println("[" + name + "] 射出了一颗子弹，还剩下" + bulletSize + "颗");
            //唤醒等待的所有线程，可以继续压入了
            notifyAll();

        }

    }

}
