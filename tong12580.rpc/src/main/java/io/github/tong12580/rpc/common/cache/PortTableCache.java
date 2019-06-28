package io.github.tong12580.rpc.common.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 端口记录表
 * PortTableCache
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-28 16:23
 */
public class PortTableCache {
    private AtomicInteger atomicInteger = new AtomicInteger();
    private volatile static List<Integer> PORT_TABLE = new ArrayList<>();

    public static void setPortTable(int port) {
        PORT_TABLE.add(port);
    }

}
