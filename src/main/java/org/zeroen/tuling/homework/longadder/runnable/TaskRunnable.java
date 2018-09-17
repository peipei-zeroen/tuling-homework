package org.zeroen.tuling.homework.longadder.runnable;

import java.util.Random;

/**
 * @Author
 * @Description
 * @Date Created in 17:30 2018/9/17
 * @Modified By：
 */
public class TaskRunnable implements Runnable {
    private final Random random = new Random();

    @Override
    public void run() {
        random.nextInt(1000);
        /*long sleep = 50 + random.nextInt(751);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
