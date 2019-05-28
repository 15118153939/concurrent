package com.ch2.forkjoin.sum;

import tools.SleepTools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author sxylml
 * @Date : 2019/5/24 16:03
 * @Description:
 */
public class SumTest {
    public static void main(String[] args) {

        int[] srcArray = MakeArray.makeArray(100000);
        sumforkjoinSum(srcArray);
        sumNormal(srcArray);


    }


    public static void sumforkjoinSum(int[] srcArray) {

        //new 出池的实例
        ForkJoinPool pool = new ForkJoinPool();
        // new 出task 实例
        SumTask innerFind = new SumTask(srcArray, 0, srcArray.length - 1);
        long start = System.currentTimeMillis();
        pool.invoke(innerFind);
        long sum = innerFind.join();
        System.out.println("sumforkjoinSum：The count is " + sum + " spend time:" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * ForkJoin执行累加
     */
    private static class SumTask extends RecursiveTask<Long> {

        /*阈值*/
        private final int THRESHOLD;
        private int[] src;
        private int fromIndex;
        private int toIndex;

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.THRESHOLD = src.length / 10;
        }

        @Override
        protected Long compute() {
            /*任务的大小是否合适*/
            if (toIndex - fromIndex < THRESHOLD) {
//                System.out.println(" from index = "+fromIndex
//                        +" toIndex="+toIndex);
                long count = 0;
                for (int i = fromIndex; i <= toIndex; i++) {
                    //SleepTools.ms(1);
                    count = count + src[i];
                }
                return count;
            } else {
                //fromIndex....mid.....toIndex 拆分子任务
                int mid = (fromIndex + toIndex) / 2;
                SumTask left = new SumTask(src, fromIndex, mid);
                SumTask right = new SumTask(src, mid + 1, toIndex);
                // 提交给 pool 执行
                invokeAll(left, right);
                // 左右两边的值
                return left.join() + right.join();
            }
        }

    }

    /**
     * 普通计算和的方法
     *
     * @param srcArray
     */
    public static void sumNormal(int[] srcArray) {

        long count = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < srcArray.length; i++) {
            //休眠下可以看到forkjoin要快
            //SleepTools.ms(1);
            count += srcArray[i];
        }
        System.out.println("sumNormal：The count is " + count + " spend time:" + (System.currentTimeMillis() - start) + "ms");
    }

}
