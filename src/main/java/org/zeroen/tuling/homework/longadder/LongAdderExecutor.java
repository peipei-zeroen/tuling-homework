package org.zeroen.tuling.homework.longadder;

import org.zeroen.tuling.homework.longadder.runnable.TaskRunnable;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Author
 * @Description
 * @Date Created in 17:36 2018/9/17
 * @Modified By：
 */
public class LongAdderExecutor {

    public void execute() {
        final int nThreads = 12;
        final int nTasks = 100000;
        //final LongAdder counter = new LongAdder();
        //final LongAdder totalExpand = new LongAdder();
        //final LongAdder contendedTime = new LongAdder();

        final AtomicLong counter = new AtomicLong();
        final AtomicLong totalExpand = new AtomicLong();
        final AtomicLong contendedTime = new AtomicLong();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()) {
            ThreadLocal<Long> starts = new ThreadLocal<>();
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                long start = System.currentTimeMillis();
                starts.set(start);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                long start = starts.get();
                long expand = System.currentTimeMillis() - start;
                String result = null == t ? "成功" : "失败";
                System.out.println(String.format("%s执行%s，耗时%dms", Thread.currentThread().getName(), result, expand));
                long contendStart = System.nanoTime();
                //counter.increment();
                //totalExpand.add(expand);
                //contendedTime.add(System.nanoTime() - contendStart);
                counter.incrementAndGet();
                totalExpand.addAndGet(expand);
                contendedTime.addAndGet(System.nanoTime() - contendStart);
            }
        };
        executor.prestartAllCoreThreads();
        Runnable runnable = new TaskRunnable();
        long start = System.currentTimeMillis();
        for (int i = 0; i < nTasks; i++) {
            executor.execute(runnable);
        }

        while (counter.longValue() < nTasks) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("完成总任务数：" + counter.longValue());
        System.out.println("任务总耗时：" + totalExpand.longValue() + "ms");
        System.out.println("平均耗时：" + ((double)totalExpand.longValue() / (double)counter.longValue()) + "ms");
        System.out.println("adder竞争时间总耗时：" + contendedTime.longValue() + "ns");
        System.out.println("===============================================================");
        System.out.println("主线程执行耗时：" + (System.currentTimeMillis() - start) + "ms");

    }
}
