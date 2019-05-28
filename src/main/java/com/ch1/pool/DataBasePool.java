package com.ch1.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author sxylml
 * @Date : 2019/5/16 14:08
 * @Description:
 */
public class DataBasePool {

    /**
     * 连接池
     */
    public static LinkedList<Connection> pool = new LinkedList<>();


    public DataBasePool(int initalSize) {
        if (initalSize > 0) {
            for (int i = 0; i < initalSize; i++) {
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    /**
     * 释放连接,通知其他的等待连接的线程
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            //1： 获得对象的锁。
            synchronized (pool) {
                //2：改变条件
                pool.addLast(connection);
                //3：通知所有等待在对象上的线程。
                pool.notifyAll();
            }
        }
    }






    /**
     *
     * @param mills
     * @return
     * @throws InterruptedException
     */

    public Connection fetchConnection(long mills) throws InterruptedException {

        //1获取对象的锁。
        synchronized (pool) {

            // 永不超时模式
            if (mills <= 0) {
                //2:如果条件不满足，那么调用对象的wait()方法，被通知后仍要检查条件。
                while (pool.isEmpty()) {
                    try {
                        pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //3:条件满足则执行对应的逻辑。
                return pool.removeFirst();
            } else {

                /*超时时刻*/
                long future = System.currentTimeMillis() + mills;
                  /*等待时长*/
                long remaining = mills;
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining);
                    /*唤醒一次，重新计算等待时长*/
                    remaining = future - System.currentTimeMillis();
                }
                Connection connection = null;
                if (!pool.isEmpty()) {
                    connection = pool.removeFirst();
                }
                return connection;

            }


        }


    }
}
