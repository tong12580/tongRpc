package io.github.tong12580.common.sync;

import io.github.tong12580.common.core.lang.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 任务同步机制
 * SyncFuture
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-11-19 16:59
 */
@Slf4j
public class SyncFuture<T> implements Future<T> {
    private final CountDownLatch latch = new CountDownLatch(1);

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
    public T get() {
        if (isDone()) {
            return this.response;
        }
        try {
            latch.await(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
            if (isDone()) {
                return this.response;
            }
        } catch (InterruptedException e) {
            log.error("Synchronization timeout exception!", e);
        }
        return null;
    }

    @Override
    public T get(long timeout, TimeUnit unit) {
        if (isDone()) {
            return this.response;
        }
        try {
            latch.await(timeout, unit);
            if (isDone()) {
                return this.response;
            }
        } catch (InterruptedException e) {
            log.error("Lock wait timeout exception!", e);
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