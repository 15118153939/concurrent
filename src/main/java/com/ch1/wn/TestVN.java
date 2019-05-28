package com.ch1.wn;

/**
 * @author sxylml
 * @Date : 2019/5/14 15:36
 * @Description:
 */
public class TestVN {
//    private static Express express = new Express(0, Express.CITY);
    private static MyExpress express = new MyExpress(0, Express.CITY);

    /**
     * 检查里程数变化的线程,不满足条件，线程一直等待
     */
    private static class CheckKm extends Thread {
        @Override
        public void run() {
            express.waitKm();
        }
    }


    /**
     * 检查地点变化的线程,不满足条件，线程一直等待
     */
    private static class CheckSite extends Thread {
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<3;i++){
            new CheckSite().start();
        }
        for(int i=0;i<3;i++){
            new CheckKm().start();
        }

        Thread.sleep(1000);
        express.changeKm();//快递地点变化
        express.changeSite();
    }

}
