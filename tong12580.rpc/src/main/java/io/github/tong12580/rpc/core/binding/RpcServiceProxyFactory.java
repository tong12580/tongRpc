package io.github.tong12580.rpc.core.binding;

/**
 * Rpc服务代理工厂
 * RpcServiceProxyFactory
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-7-11 14:44
 */
public class RpcServiceProxyFactory<T> {
    private final Class<T> mapperInterface;

    public RpcServiceProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }
}
