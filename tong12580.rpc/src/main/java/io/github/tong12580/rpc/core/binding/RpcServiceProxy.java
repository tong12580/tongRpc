package io.github.tong12580.rpc.core.binding;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Rpc服务代理
 * RpcServiceProxy
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-7-11 16:53
 */
public class RpcServiceProxy<T> implements InvocationHandler, Serializable {

    private Class<T> tClass;
    private String methodName;

    public RpcServiceProxy(Class<T> className, String methodName) {
        this.tClass = className;
        this.methodName = methodName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method = tClass.getMethod(methodName);
        return method.invoke(tClass.newInstance(), args);
    }
}
