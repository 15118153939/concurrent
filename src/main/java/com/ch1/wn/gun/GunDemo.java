package com.ch1.wn.gun;

import tools.SleepTools;

/**
 * @author sxylml
 * @Date : 2019/5/14 17:06
 * @Description: 采用多线程技术，例如wait/notify，设计实现一个符合生产者和消费者问题的程序，
 * 对某一个对象（枪膛）进行操作，其最大容量是20颗子弹，
 * 生产者线程是一个压入线程，它不断向枪膛中压入子弹，消费者线程是一个射出线程，它不断从枪膛中射出子弹。
 */
public class GunDemo {

    public static void main(String[] args) {
        Gun gun = new Gun();
        new Thread(new PressIn(gun)).start();
        new Thread(new Shooting(gun)).start();

    }

    static class PressIn implements Runnable {
        Gun gun;

        public PressIn(Gun gun) {
            this.gun = gun;
        }

        @Override
        public void run() {
            while (true) {
                gun.pressInBullet();
                SleepTools.ms(400);
            }
        }
    }

    static class Shooting implements Runnable {

        Gun gun;

        public Shooting(Gun gun) {
            this.gun = gun;
        }

        @Override
        public void run() {
            while (true) {
                gun.shooting();
                SleepTools.ms(600);
            }
        }
    }


    static public class Gun {

        /**
         * 容量20
         */
        private final static int CAPACITY = 20;

        private int currentBulletNumber;

        synchronized void pressInBullet() {

            while (currentBulletNumber >= CAPACITY) {
                System.out.println("弹夹已满，等待射击");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            currentBulletNumber++;
            System.out.println("弹匣未满，开始装弹，现有子弹:" + currentBulletNumber);
            notifyAll();


        }

        synchronized void shooting() {

            while (currentBulletNumber <= 0) {
                System.out.println("弹匣已空，无法射击");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("弹匣有弹，开始射击，现有子弹:" + currentBulletNumber);
            currentBulletNumber--;
            System.out.println("射击完还剩子弹:" + currentBulletNumber);
            notifyAll();


        }
    }


}
