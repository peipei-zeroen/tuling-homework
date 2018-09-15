package org.zeroen.tuling.homework.lock;

import org.zeroen.tuling.homework.lock.runnable.IMointerRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWrite实现
 *
 * @Author
 * @Description
 * @Date Created in 20:47 2018/9/13
 * @Modified By：
 */
public class ReetrantReadWriteLockHomework extends AbstractSeckill {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private MointerRunnable mointerRunnable;

    public ReetrantReadWriteLockHomework(int itemLength) {
        super(itemLength);
    }

    @Override
    public void buy(String item) {
        buycount.incrementAndGet();

        try {
            writeLock.lock();
            Integer num = cache.get(item);
            if (num == null) {
                System.out.println(String.format("对不起商品%s不存在！", item));
                return;
            }
            if (num <= 0) {
                System.out.println(String.format("对不起商品%s已售完！", item));
                return;
            }
            System.out.println(String.format("商品%s被秒杀一件！", item));
            num--;
            cache.put(item, num);
        } finally {
            if (writeLock.isHeldByCurrentThread())
                writeLock.unlock();
        }
    }

    @Override
    public int showRemaining(String item) {
        showcount.incrementAndGet();

        Integer num = -1;
        readLock.lock();
        try {
            num = cache.get(item);
            return num;
        } finally {
            readLock.unlock();
            if (num == null) {
                System.out.println(String.format("对不起商品%s不存在！", item));
            } else if (num < 0) {
                System.out.println("系统不可知异常！");
            }
            System.out.println(String.format("此时商品%s还剩余%d件！", item, num));
        }
    }

    @Override
    public IMointerRunnable getMointerRunnable() {
        if (mointerRunnable == null) {
            mointerRunnable = new MointerRunnable();
        }
        return mointerRunnable;
    }

    private class MointerRunnable implements IMointerRunnable {

        private final List<ExecutorService> toBeStoppedExecutors = new ArrayList<>();

        private final List<Thread> toBeStoppedThreads = new ArrayList<>();

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (true) {
                boolean soldout = true;
                readLock.lock();
                try {
                    soldout = isSoldout();
                } finally {
                    readLock.unlock();
                }

                if (soldout) {
                    fireOnSoldOut();
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("运行完毕，耗时： " + (System.currentTimeMillis() - start) + " ms");
        }

        private boolean isSoldout() {
            boolean soldout = true;
            for (Map.Entry<String, Integer> entry : cache.entrySet()) {
                Integer v = entry.getValue();
                if (v != null && v > 0) {
                    soldout = false;
                    break;
                }
            }
            return soldout;
        }

        @Override
        public void addToBeStopped(Thread t) {
            toBeStoppedThreads.add(t);
        }

        @Override
        public void addToBeStopped(ExecutorService executor) {
            toBeStoppedExecutors.add(executor);
        }

        @Override
        public void fireOnSoldOut() {
            for (Thread t : toBeStoppedThreads) {
                t.interrupt();
            }
            for (ExecutorService es : toBeStoppedExecutors) {
                es.shutdownNow();
            }
        }
    }
}
