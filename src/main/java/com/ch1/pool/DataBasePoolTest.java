package com.ch1.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sxylml
 * @Date : 2019/5/16 14:50
 * @Description:
 */
public class DataBasePoolTest {


    static DataBasePool pool = new DataBasePool(10);

    /**
     * 控制器:控制main线程将会等待所有Woker结束后才能继续执行
     */
    static CountDownLatch countDownLatch;


    public static void main(String[] args) throws InterruptedException {

        int threadCount = 50;
        countDownLatch = new CountDownLatch(threadCount);
        //每个线程的操作次数
        int count = 20;
        //计数器：统计可以拿到连接的线程
        AtomicInteger got = new AtomicInteger();
        //计数器：统计没有拿到连接的线程
        AtomicInteger notGot = new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            new Thread(new Worker(count, got, notGot), "worker_" + i).start();


        }
        countDownLatch.await();// main线程在此处等待
        System.out.println("总共尝试了: " + (threadCount * count));
        System.out.println("拿到连接的次数：  " + got);
        System.out.println("没能连接的次数： " + notGot);

    }

    static class Worker implements Runnable {

        /**
         * 每个线程的操作次数
         */
        int count;

        /**
         * 计数器：统计可以拿到连接的线程
         */
        AtomicInteger got;

        /**
         * 计数器：统计没有拿到连接的线程
         */
        AtomicInteger notGot;

        public Worker(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            while (count > 0) {
                try {
                    Connection connection = pool.fetchConnection(1000);

                    if (connection != null) {
                        //获取到连接
                        try {
                            //模拟数据操作
                            connection.createStatement();
                            //PreparedStatement preparedStatement = connection.prepareStatement("");
                            //preparedStatement.execute();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            //使用完毕归还连接
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                            System.out.println(Thread.currentThread().getName() + "获取到连接使用后并且归还!次数：" + got);
                        }

                    } else {
                        // 没有获取到连接
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName() + "等待超时!");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }
            countDownLatch.countDown();
        }
    }

}
