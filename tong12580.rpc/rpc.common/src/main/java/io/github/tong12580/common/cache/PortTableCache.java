package io.github.tong12580.common.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 端口记录表
 * PortTableCache
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-28 16:23
 */
public class PortTableCache {
    private static Lock lock = new ReentrantLock();
    private volatile static List<Integer> PORT_TABLE = new ArrayList<>();

    public static void setPortTable(int port) {
        try {
            if (lock.tryLock()) {
                PORT_TABLE.add(port);
            }
        } finally {
            lock.unlock();
        }
    }

    public static boolean containsKey(int port) {
        try {
            if (lock.tryLock()) {
                return PORT_TABLE.contains(port);
            }
            return containsKey(port);
        } finally {
            lock.unlock();
        }
    }

}
