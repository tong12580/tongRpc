package io.github.tong12580.rpc.exceptions;

/**
 * 缓存异常
 * SyncCacheException
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-24 18:50
 */
public class SyncCacheException extends RuntimeException {
    public SyncCacheException() {
        super();
    }

    public SyncCacheException(String msg) {
        super(msg);
    }

    public SyncCacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyncCacheException(Throwable cause) {
        super(cause);
    }

}
