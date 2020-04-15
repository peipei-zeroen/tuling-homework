package org.zeroen.tuling.homework.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @Author
 * @Description
 * @Date Created in 22:08 2018/9/20
 * @Modified By：
 */
public abstract class MyAbstractExecutor implements MyExecutor {

    protected <T> RunnableFuture<T> newFutureTask(Runnable runnable, T value) {
        return new FutureTask<T>(runnable, value);
    }

    protected <T> RunnableFuture<T> newFutureTask(Callable<T> callable) {
        return new FutureTask<T>(callable);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        assertTask(task);
        RunnableFuture<T> ftask = newFutureTask(task);
        execute(ftask);
        return ftask;
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        assertTask(task);
        RunnableFuture<T> ftask = newFutureTask(task, result);
        execute(ftask);
        return ftask;
    }

    @Override
    public Future<?> submit(Runnable task) {
        assertTask(task);
        RunnableFuture<Void> ftask = newFutureTask(task, null);
        execute(ftask);
        return ftask;
    }

    protected void assertTask(Callable<?> task) {
        if (task == null)
            throw new NullPointerException();
    }

    protected void assertTask(Runnable task) {
        if (task == null)
            throw new NullPointerException();
    }
}
