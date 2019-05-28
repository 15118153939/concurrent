package com.ch1.syn;

/**
 * @author sxylml
 * @Date : 2019/5/10 15:05
 * @Description: 错误的加锁和原因分析
 */
public class TestIntegerSyn {

    public static void main(String[] args) {
        Worker worker = new Worker(1);
        for (int i = 0; i < 5; i++) {
            new Thread(worker).start();
        }
    }

    private static class Worker implements Runnable {

        private Integer i;
        private Object o = new Object();

        public Worker(Integer i) {
            this.i = i;
        }

        @Override
        public void run() {
//           错误的加锁： 锁的对象i 发生了变化，导致结果并不正确, 所以需要正确的加锁：锁的对象要不变。是同一个对象
            synchronized (i) {
//                正确的加锁
//            synchronized (o) {
//            synchronized (this) {
                Thread thread = Thread.currentThread();System.out.println(thread.getName() + "--@" + System.identityHashCode(i));
//                反编译后可以发现 i++不是原子性的 会创建新的对象 参考 valueOf
                i++;
//                正确应该：2，3，4，5，6                    System.identityHashCode 近似的理解为地址
                System.out.println("线程名：" + thread.getName() + "-------i的值[" + i + "]  i的地址 -@" + System.identityHashCode(i));
                System.out.println("线程名：" + thread.getName() + "-------i的值[" + i + "] this 的地址 -@" + System.identityHashCode(this));
                System.out.println("线程名：" + thread.getName() + "-------i的值[" + i + "]  o的地址 -@" + System.identityHashCode(o));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println(thread.getName() + "-------" + i + "--@" + System.identityHashCode(i));
            }
        }
    }
}
