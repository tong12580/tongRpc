package io.github.tong12580.rpc.core.sync;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <p>SyncCachePool</p>
 * <span>异步信息同步用缓存缓存</span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/23 17:35
 */
public class SyncCachePool {
    private volatile static Map<Long, SyncFuture> SyncFutureMap = Collections.synchronizedMap(new WeakHashMap<>());

    private SyncCachePool() {
    }

    public void cache(Long reqId, SyncFuture syncFuture) {
        if (null == reqId || null == syncFuture) {
            throw new SecurityException("ReqId or syncFuture is null!");
        }
        SyncFutureMap.put(reqId, syncFuture);
    }

    public SyncFuture get(Long reqId) {
        if (null == reqId) {
            throw new SecurityException("ReqId is null!");
        }
        return SyncFutureMap.get(reqId);
    }

    public void deleteCache(Long reqId) {
        if (null == reqId) {
            throw new SecurityException("ReqId is null!");
        }
        SyncFutureMap.remove(reqId);
    }

    public boolean containsKey(Long reqId) {
        if (null == reqId) {
            throw new SecurityException("ReqId is null!");
        }
        return SyncFutureMap.containsKey(reqId);
    }

    public static SyncCachePool getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        /**
         * 实例
         */
        INSTANCE;
        private SyncCachePool syncCachePool;

        Singleton() {
            syncCachePool = new SyncCachePool();
        }
        public SyncCachePool getInstance() {
            return syncCachePool;
        }
    }
}
