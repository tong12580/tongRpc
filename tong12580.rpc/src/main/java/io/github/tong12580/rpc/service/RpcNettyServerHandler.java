package io.github.tong12580.rpc.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Rpc服务处理器
 * RpcNettyServerHandler
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-21 14:44
 */
public class RpcNettyServerHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
