package com.ch2.forkjoin.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * @author sxylml
 * @Date : 2019/5/25 16:04
 * @Description: 演示Exchange用法
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchange = new Exchanger<Set<String>>();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //存放数据的容器
                Set<String> setA = new HashSet<String>();
                try {
                    /*添加数据
                     * set.add(.....)
                	 * */

                    setA.add("A");
                    setA.add("a");
                    //交换set
                    setA = exchange.exchange(setA);

                    setA.forEach(src -> System.out.println("setA"+src));
                	/**处理交换后的数据*/
                } catch (InterruptedException e) {
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 存放数据的容器
                 */
                Set<String> setB = new HashSet<String>();
                try {
                	/*添加数据
                	 * set.add(.....)
                	 * set.add(.....)
                	 * */
                    setB.add("B");
                    setB.add("b");
                    //交换set
                    setB = exchange.exchange(setB);
                	/**处理交换后的数据*/
                    setB.forEach(src -> System.out.println("setB:" + src));
                } catch (InterruptedException e) {
                }
            }
        }).start();

    }
}
