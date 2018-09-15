package org.zeroen.tuling.homework.lock;

import org.zeroen.tuling.homework.lock.runnable.IMointerRunnable;

/**
 * 以秒杀场景模拟各种锁的并发情况
 * 此接口定义买、查看行为
 * @Author  zeroen
 * @Description 秒杀场景接口
 * @Date Created in 1:20 2018/9/14
 * @Modified By：zeroen
 */
public interface ISeckill {

    /**
     * 购买 item
     * @param item 购买 item
     */
    void buy(String item);

    /**
     * 当前item余量
     * @return 当前item余量
     */
    int showRemaining(String item);

    /**
     * 获取秒杀监视器
     * @return 秒杀监视器
     */
    IMointerRunnable getMointerRunnable();
}
