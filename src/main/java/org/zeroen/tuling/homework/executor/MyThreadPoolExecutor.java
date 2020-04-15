package org.zeroen.tuling.homework.executor;

/**
 * @Author
 * @Description
 * @Date Created in 22:23 2018/9/20
 * @Modified By：
 */
public class MyThreadPoolExecutor extends MyAbstractExecutor {


    @Override
    public void execute(Runnable command) {
        assertTask(command);

        //按3个步骤进行

    }

    @Override
    public void shutdown() {

    }
}
