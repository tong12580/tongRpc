package io.github.tong12580.common.exceptions;

/**
 * RpcClientException
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-11-22 16:30
 */
public class RpcClientException extends RuntimeException {
    public RpcClientException() {
        super();
    }

    public RpcClientException(String msg) {
        super(msg);
    }

    public RpcClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcClientException(Throwable cause) {
        super(cause);
    }
}
