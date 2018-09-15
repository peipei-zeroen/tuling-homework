package org.zeroen.tuling.homework.lock.runnable;

import java.util.concurrent.ExecutorService;

/**
 * @Author
 * @Description
 * @Date Created in 2:02 2018/9/14
 * @Modified By：
 */
public interface IMointerRunnable extends Runnable {
    /**
     * 触发商品被秒杀完事件
     */
    void fireOnSoldOut();

    void addToBeStopped(Thread t);

    void addToBeStopped(ExecutorService executor);
}
