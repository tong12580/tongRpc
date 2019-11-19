package io.github.tong12580.rpc.common.cache;

import io.github.tong12580.rpc.service.RpcServer;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通道缓存
 * ChannelCache
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-7-11 17:59
 */
public class ChannelCache {
    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public static void add(String name, Channel channel) {
        channelMap.put(name, channel);
    }

    public static Channel get(String name) {
        Channel channel = channelMap.get(name);
        if (null == channel && Constants.RPC_SERVER.equals(name)) {
            RpcServer rpcServer = RpcServer.builder();
            if (null != rpcServer) {
                channelMap.put(Constants.RPC_SERVER, rpcServer.getChannel());
                return rpcServer.getChannel();
            }
        }
        return channel;
    }
}
