package com.ch1.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author sxylml
 * @Date : 2019/5/15 17:26
 * @Description: 连接池的实现
 */
//public class DBPool {
//
//    /**
//     * 容器，存放连接
//     */
//    private static LinkedList<Connection> pool = new LinkedList<Connection>();
//
//
//    /**
//     * 限制了池的大小=20
//     */
//    public DBPool(int initialSize) {
//        if (initialSize > 0) {
//            for (int i = 0; i < initialSize; i++) {
//                pool.addLast(SqlConnectImpl.fetchConnection());
//            }
//        }
//    }
//
//
//    /**
//     * 释放连接,通知其他的等待连接的线程
//     */
//    public void releaseConnection(Connection connection) {
//        if (connection != null) {
//            //1获得对象的锁。
//            synchronized (pool) {
//                //2改变条件。
//                pool.addLast(connection);
//                //3通知所有等待在对象上的线程。
//                pool.notifyAll();
//            }
//        }
//    }
//
//
//    /**
//     * 等待超时模式实现一个数据库连接池
//     * 在mills内无法获取到连接，将会返回null 1S
//     * <p>
//     * 1）获取对象的锁。
//     * 2）如果条件不满足，那么调用对象的wait()方法，被通知后仍要检查条件。
//     * 3）条件满足则执行对应的逻辑。
//     *
//     * @param mills
//     * @return
//     */
//    public Connection fetchConnection(long mills) throws InterruptedException {
//
//        //1）获取对象的锁。
//        synchronized (pool) {
////            永不超时模式
//            if (mills <= 0) {
//                while (pool.isEmpty()) {
//                    //如果条件不满足，那么调用对象的wait()方法
//                    pool.wait();
//                }
//                return pool.removeFirst();
//            } else {
//                //超时模式
//                 /*超时时刻*/
//                long future = System.currentTimeMillis() + mills;
//                  /*等待时长*/
//                long remaining = mills;
//
//                while (pool.isEmpty() && remaining > 0) {
//                    pool.wait(remaining);
//                    /*唤醒一次，重新计算等待时长*/
//                    remaining = future - System.currentTimeMillis();
//                }
//                Connection connection = null;
//                if (!pool.isEmpty()) {
//                    connection = pool.removeFirst();
//                }
//                return connection;
//            }
//        }
//    }
//}
//


public class DBPool {

    /*容器，存放连接*/
    private static LinkedList<Connection> pool = new LinkedList<Connection>();

    /*限制了池的大小=20*/
    public DBPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    /*释放连接,通知其他的等待连接的线程*/
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool){
                pool.addLast(connection);
                //通知其他等待连接的线程
                pool.notifyAll();
            }
        }
    }

    /*获取*/
    // 在mills内无法获取到连接，将会返回null 1S
    public Connection fetchConnection(long mills)
            throws InterruptedException {
        synchronized (pool){
            //永不超时
            if(mills<=0){
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                /*超时时刻*/
                long future = System.currentTimeMillis()+mills;
                /*等待时长*/
                long remaining = mills;
                while(pool.isEmpty()&&remaining>0){
                    pool.wait(remaining);
                    /*唤醒一次，重新计算等待时长*/
                    remaining = future-System.currentTimeMillis();
                }
                Connection connection = null;
                if(!pool.isEmpty()){
                    connection = pool.removeFirst();
                }
                return connection;
            }
        }

    }
}