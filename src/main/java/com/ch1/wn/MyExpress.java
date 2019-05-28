package com.ch1.wn;

/**
 * @author sxylml
 * @Date : 2019/5/14 16:09
 * @Description: 模拟快递
 */
public class MyExpress {

    public final static String CITY = "ShengHai";

    private int km;
    private String site;

    public MyExpress(int km, String site) {
        this.km = km;
        this.site = site;
    }

    /**
     * 通知方遵循如下原则。
     * 1）获得对象的锁。
     * 2）改变条件。
     * 3）通知所有等待在对象上的线程。
     */

    public synchronized void changeKm() {
        this.km = 101;
        notifyAll();

//        todo
    }

    public synchronized void changeSite() {
//        todo
        this.site = "BeiJing";
        notifyAll();
    }


    /**
     * 等待方遵循如下原则。
     * 1）获取对象的锁。
     * 2）如果条件不满足，那么调用对象的wait()方法，被通知后仍要检查条件。
     * 3）条件满足则执行对应的逻辑。
     */

    public synchronized void waitKm() {

        while (this.km < 100) {
            try {
                wait();
                System.out.println("Check km thread[" + Thread.currentThread().getId() + "] is be notified ，km=" + this.km);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("里程数：" + this.km + " 保存入库咯");

    }


    public synchronized void waitSite() {
        //到达目的地
        while (this.site.equals(CITY)) {

            try {
                wait();
                System.out.println("Check site thread[" + Thread.currentThread().getId() + "] is be notified ，site=" + this.site);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("the site is " + this.site + ",I will call user");
        }

    }

}
