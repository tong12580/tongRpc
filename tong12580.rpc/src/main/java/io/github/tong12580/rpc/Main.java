package io.github.tong12580.rpc;

import io.github.tong12580.rpc.client.RpcClient;
import io.github.tong12580.rpc.common.message.RequestMessage;
import io.github.tong12580.rpc.service.RpcServer;
import io.netty.channel.Channel;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试主程序
 * Main
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-25 11:20
 */
public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactory() {
                    private final AtomicInteger threadNumber = new AtomicInteger(1);
                    private final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

                    @Override
                    public Thread newThread(Runnable r) {
                        SecurityManager s = System.getSecurityManager();
                        ThreadGroup group = (s != null) ? s.getThreadGroup() :
                                Thread.currentThread().getThreadGroup();
                        String namePrefix = "Rpc-" + POOL_NUMBER.getAndIncrement() +
                                "-thread-";
                        Thread t = new Thread(group, r,
                                namePrefix + threadNumber.getAndIncrement(),
                                0);
                        if (t.isDaemon()) {
                            t.setDaemon(false);

                        }
                        if (t.getPriority() != Thread.NORM_PRIORITY) {
                            t.setPriority(Thread.NORM_PRIORITY);
                        }
                        return t;
                    }
                });
        executorService.submit(new RpcServer(8080, 1, 1));
        executorService.submit(new RpcClient(8080, "127.0.0.1", 1));
    }
}
