package com.ch1.threadlocal;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sxylml
 * @Date : 2019/5/10 16:45
 * @Description: 1B（Byte字节）=8bit
 * <p>
 * 1KB (Kilobyte 千字节)=1024B，
 * <p>
 * 1MB (Mega byte 兆字节 简称“兆”)=1024KB，
 * <p>
 * 1GB (Giga byte 吉字节 又称“千兆”)=1024MB，
 * <p>
 * 1TB (Tera byte 万亿字节 太字节)=1024GB，其中1024=2^10 ( 2 的10次方)，
 * <p>
 * 1PB（Peta byte 千万亿字节 拍字节）=1024TB，
 * <p>
 * 1EB（Exa byte 百亿亿字节 艾字节）=1024PB，
 * <p>
 * 1ZB (Zetta byte 十万亿亿字节 泽字节)= 1024 EB,
 * <p>
 * 1YB (Yotta byte 一亿亿亿字节 尧字节)= 1024 ZB,
 * <p>
 * 1BB (Bronto byte 一千亿亿亿字节)= 1024 YB
 * <p>
 * 1NB(Nona byte )= 1024BB
 * <p>
 * 1DB(Dogga byte)= 1024NB
 */
public class ThreadLocalMemoryLeak {
    private static final int TASK_LOOP_SIZE = 100;

    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    static class LocalVariable {
        /**
         * 5M大小的数组  1MB=1024KB
         */
        private byte[] a = new byte[1024 * 1024 * 5];
    }

    ThreadLocal<LocalVariable> localVariable;
    //= new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        /*5*5=25*/
        for (int i = 0; i < TASK_LOOP_SIZE; ++i) {
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // 场景1：什么都不执行


                    // 场景2：每个任务中new出一个数组
                    //new LocalVariable();


                    //场景3 启用ThreadLocal 没有使用remove()
                    ThreadLocalMemoryLeak oom = new ThreadLocalMemoryLeak();
                    oom.localVariable = new ThreadLocal<>();
                    oom.localVariable.set(new LocalVariable());
                    // new LocalVariable();

                    System.out.println("use local varaible");
                    // 通过 visualVM 查看内存使用情况，没用调用remove() 和调用remove() 的差距

                    // 场景4：在3的基础上使用remove()
                    oom.localVariable.remove();


                }
            });

            Thread.sleep(100);
        }
        System.out.println("pool execute over");
    }
}
