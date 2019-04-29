package com.ch1.syn;

/**
 * @author sxylml
 * @Date : 2019/4/26 13:29
 * @Description:
 */
public class SynTest {

    private long count = 0;
    /**
     * 作为一个锁
     */
    private Object object = new Object();
    private int anInt;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void incCount() {
        this.count++;
    }

    /**
     * synchronized 用在同步块上
     */
    public void incCount2() {
        synchronized (object) {
            this.count++;
        }
    }

    /**
     * 用在方法上
     */
    public synchronized void incCount3() {
        this.count++;
    }

    /**
     * 用在同步块上，但是锁的是当前类的对象实例
     */
    public void incCount4() {
        synchronized (this) {
            this.count++;
        }
    }


    private static class Count extends Thread {

        private SynTest simplOper;

        public Count(SynTest simplOper) {
            this.simplOper = simplOper;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                //1：没加锁结果 大部分应该不全等于预计的值
                simplOper.incCount();
//               加锁后结果是正确的
//                simplOper.incCount2();
//                simplOper.incCount3();
//                simplOper.incCount4();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        int max = 100;
        for (int i = 0; i < max; i++) {
            SynTest simplOper = new SynTest();
//            循环启动 max 个线程
            for (int j = 0; j < max; j++) {
                new Count(simplOper).start();
            }
            Thread.sleep(100);
            System.out.println("第" + (i + 1) + "次测试：count = " + simplOper.count);
        }


    }

}
