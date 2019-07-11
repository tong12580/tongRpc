package io.github.tong12580.rpc.core.binding;

import io.github.tong12580.rpc.common.Constants;
import io.github.tong12580.rpc.common.cache.ChannelCache;
import io.netty.channel.Channel;

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

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //TODO 1
        Channel channel = ChannelCache.get(Constants.RPC_SERVER);

        Object object = method.invoke(this, args);
        //TODO 2
        return object;
    }
}
