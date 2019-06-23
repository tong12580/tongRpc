package io.github.tong12580.rpc.core.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <p>SyncFuture</p>
 * <span>任务同步机制</span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/23 17:24
 */
public class SyncFuture<T> implements Future<T> {
    private CountDownLatch latch = new CountDownLatch(1);

    private T response;
    private long beginTime = System.currentTimeMillis();

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return null != response;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        return this.response;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (latch.await(timeout, unit)) {
            return this.response;
        }
        return null;
    }

    public void setResponse(T response) {
        this.response = response;
        latch.countDown();
    }

    public long getBeginTime() {
        return beginTime;
    }
}
