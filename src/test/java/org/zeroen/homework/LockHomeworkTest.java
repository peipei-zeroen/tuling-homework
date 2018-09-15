package org.zeroen.homework;

import org.junit.Test;
import org.zeroen.tuling.homework.lock.ReetrantReadWriteLockHomework;
import org.zeroen.tuling.homework.lock.StampedLockHomework;
import org.zeroen.tuling.homework.lock.runnable.BuyRunnable;
import org.zeroen.tuling.homework.lock.runnable.IMointerRunnable;
import org.zeroen.tuling.homework.lock.runnable.ShowRunnable;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

/**
 * @Author
 * @Description
 * @Date Created in 2:19 2018/9/14
 * @Modified By：
 */
public class LockHomeworkTest {

    @Test
    public void test() {
        final ThreadPoolExecutor buyers = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        final ThreadPoolExecutor showers = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);

        StampedLockHomework instance = new StampedLockHomework(100);
//        ReetrantReadWriteLockHomework instance = new ReetrantReadWriteLockHomework(100);

        final Random random = new Random();
        final BuyRunnable buyRunnable = new BuyRunnable(instance);
        final ShowRunnable showRunnable = new ShowRunnable(instance);

        Thread runner = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().interrupted()) {
                    System.out.println("结束模拟秒杀请求...");
                    break;
                }
                IntStream.range(0, 10000).forEach(i -> {
                    int r = random.nextInt(100);
                    if (r < 90) {
                        showers.execute(showRunnable);
                    } else {
                        buyers.execute(buyRunnable);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        IMointerRunnable mointerRunnable = instance.getMointerRunnable();
        mointerRunnable.addToBeStopped(buyers);
        mointerRunnable.addToBeStopped(showers);
        mointerRunnable.addToBeStopped(runner);

        Thread monitor = new Thread(mointerRunnable);
        monitor.setDaemon(true);

        buyers.prestartAllCoreThreads();
        showers.prestartAllCoreThreads();
        runner.start();
        monitor.start();

        try {
            monitor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("buy  请求数量：" + instance.getBuycount());
        System.out.println("show 请求数量：" + instance.getShowcount());
        System.out.println("总请求数量：" + instance.getTotalCount());
    }
}
