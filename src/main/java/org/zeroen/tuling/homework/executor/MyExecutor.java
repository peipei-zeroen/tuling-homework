package org.zeroen.tuling.homework.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @Author
 * @Description
 * @Date Created in 22:01 2018/9/20
 * @Modified By：
 */
public interface MyExecutor {

    void execute(Runnable command);

    <T> Future<T> submit(Callable<T> task);

    <T> Future<T> submit(Runnable task, T result);

    Future<?> submit(Runnable task);

    void shutdown();

}