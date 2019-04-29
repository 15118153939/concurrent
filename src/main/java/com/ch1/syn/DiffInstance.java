package com.ch1.syn;

/**
 * @author sxylml
 * @Date : 2019/4/26 14:35
 * @Description: 锁的实例不一样，也是可以并行的
 */
public class DiffInstance {

    private static class InstanceSyn implements Runnable{
        @Override
        public void run() {

        }
    }

}
