package org.zeroen.tuling.homework.lock;

import org.zeroen.tuling.homework.lock.runnable.IMointerRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock实现
 *
 * @Author
 * @Description
 * @Date Created in 20:47 2018/9/13
 * @Modified By：
 */
public class StampedLockHomework extends AbstractSeckill {

    private final StampedLock lock = new StampedLock();

    private MointerRunnable mointerRunnable;

    public StampedLockHomework(int itemLength) {
        super(itemLength);
    }

    @Override
    public void buy(String item) {
        buycount.incrementAndGet();

        /*long stamp = lock.tryOptimisticRead();
        Integer num = cache.get(item);
        if (lock.validate(stamp)) {
            if (num == null) {
                System.out.println(String.format("对不起商品%s不存在！", item));
            } else if (num <= 0) {
                System.out.println(String.format("对不起商品%s已售完！", item));
            } else {
                try {
                    //升级为写锁
                    stamp = lock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) { //尝试失败
                        stamp = lock.writeLock();
                    }
                    System.out.println(String.format("商品%s被秒杀一件！", item));
                    num--;
                    cache.put(item, num);
                } finally {
                    lock.unlockWrite(stamp);
                }
            }
            return;
        }*/

        long stamp = lock.readLock();
        try {
            //stamp = lock.readLock();
            //num = cache.get(item);

            Integer num = cache.get(item);
            if (num == null) {
                System.out.println(String.format("对不起商品%s不存在！", item));
                return;
            }
            if (num <= 0) {
                System.out.println(String.format("对不起商品%s已售完！", item));
                return;
            }
            //升级为写锁
            long ws = lock.tryConvertToWriteLock(stamp);
            if (ws == 0L) { //尝试失败
                lock.unlockRead(stamp);
                stamp = lock.writeLock();
            } else {
                stamp = ws;
            }
            System.out.println(String.format("商品%s被秒杀一件！", item));
            num--;
            cache.put(item, num);
        } finally {
            //lock.unlockWrite(stamp);
            lock.unlock(stamp);
        }
    }

    @Override
    public int showRemaining(String item) {
        showcount.incrementAndGet();

        Integer num = -1;
        final int retry = 3; //乐观自旋3次
        long stamp = -1L;
        try {
            for (int i = 0; i < retry; i++) {
                stamp = lock.tryOptimisticRead(); //尝试乐观锁
                // 先不管先取一次数据
                num = cache.get(item);
                if (lock.validate(stamp)) {
                    return num;
                }
            }
            try {
                // 如果乐观读失败则获取一个共享读锁（悲观获取）
                stamp = lock.readLock();
                num = cache.get(item);
            } finally {
                lock.unlockRead(stamp);
            }
            return num;
        } finally {
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
//                long stamp = lock.tryOptimisticRead();
//                soldout = isSoldout();
//                if (!lock.validate(stamp)) {
                    long stamp = lock.readLock();
                    try {
//                        stamp = lock.readLock();
                        soldout = isSoldout();
                    } finally {
                        lock.unlockRead(stamp);
                    }
//                }

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
