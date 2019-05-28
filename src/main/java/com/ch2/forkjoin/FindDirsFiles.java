package com.ch2.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author sxylml
 * @Date : 2019/5/25 09:29
 * @Description: 遍历指定目录（含子目录）找寻指定类型文件
 */
public class FindDirsFiles extends RecursiveAction {
    private File path;
    private String suffix;

    public FindDirsFiles(File path, String suffix) {
        this.path = path;
        this.suffix = suffix;
    }

    @Override
    protected void compute() {
        List<FindDirsFiles> subTasks = new ArrayList<>();
        File[] files = path.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //对每一个子目录创建一个子任务
                    subTasks.add(new FindDirsFiles(file, suffix));
                } else {
                    //文件，就判断后缀寻找自己需要的
                    if (file.getAbsolutePath().endsWith(suffix)) {
                        System.out.println(file.getAbsolutePath());
                    }
                }

            }

            if (!subTasks.isEmpty()) {
                // 在当前的 ForkJoinPool 上调度所有的子任务。
                for (FindDirsFiles subTask : invokeAll(subTasks)) {
                    subTask.join();
                }
            }
        }
    }

    public static void findDirsFiles(String path, String suffix) {

        try {
            // 用一个 ForkJoinPool 实例调度总任务
            ForkJoinPool pool = new ForkJoinPool();
            FindDirsFiles task = new FindDirsFiles(new File(path), suffix);
            //异步提交
            pool.execute(task);

             /*主线程做自己的业务工作*/
            System.out.println("Task is Running......");
            Thread.sleep(1);
            int otherWork = 0;
            for (int i = 0; i < 100; i++) {
                otherWork = otherWork + i;
            }
            System.out.println("Main Thread done sth......,otherWork=" + otherWork);
            //阻塞方法
            task.join();
            System.out.println("Task end");
        } catch (Exception e) {

        }

        
    }


    public static void main(String[] args) {
        findDirsFiles("C:/","java");
    }
}
