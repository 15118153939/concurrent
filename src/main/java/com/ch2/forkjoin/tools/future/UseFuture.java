package com.ch2.forkjoin.tools.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author sxylml
 * @Date : 2019/5/25 16:58
 * @Description:
 */
public class UseFuture {

    private static class UseCallable implements Callable<Integer> {
        int sum = 0;

        @Override
        public Integer call() throws Exception {
            System.out.println("Callable子线程开始计算！");

            for (int i = 0; i < 5000; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Callable子线程计算任务中断！");
                    return null;
                }
                sum = sum + i;
                System.out.println("sum=" + sum);
            }
            System.out.println("Callable子线程计算结束！结果为: " + sum);
            return sum;
        }
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        UseCallable useCallable = new UseCallable();
        //包装
        FutureTask<Integer> futureTask = new FutureTask<>(useCallable);
        Random r = new Random();
        new Thread(futureTask).start();

        Thread.sleep(1);
        if (r.nextInt(100) > 50) {
            System.out.println("Get UseCallable result = " + futureTask.get());
        } else {
            System.out.println("Cancel................. ");
            futureTask.cancel(true);
        }

//        System.out.println("Get UseCallable result = " + futureTask.get());
//        futureTask.cancel(true);
    }

}
