package org.zeroen.tuling.homework.lock.runnable;

import org.zeroen.tuling.homework.lock.AbstractSeckill;

/**
 * @Author
 * @Description
 * @Date Created in 1:54 2018/9/14
 * @Modified By：
 */
public class ShowRunnable implements Runnable {
    private final AbstractSeckill instance;

    public ShowRunnable(AbstractSeckill instance) {
        this.instance = instance;
    }

    @Override
    public void run() {
        instance.showRemaining(instance.randomGetItemKey());
    }
}
