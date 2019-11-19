package io.github.tong12580.rpc.core.binding;

import java.lang.reflect.Proxy;

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
    private final String methodName;

    public RpcServiceProxyFactory(Class<T> mapperInterface, String methodName) {
        this.mapperInterface = mapperInterface;
        this.methodName = methodName;
    }

    @SuppressWarnings("unchecked")
    private T newInstance(RpcServiceProxy rpcServiceProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, rpcServiceProxy);
    }

    public T newInstance() {
        final RpcServiceProxy<T> rpcServiceProxy = new RpcServiceProxy<>(mapperInterface, methodName);
        return newInstance(rpcServiceProxy);
    }
}
