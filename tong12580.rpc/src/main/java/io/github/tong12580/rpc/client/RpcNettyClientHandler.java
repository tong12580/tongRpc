package io.github.tong12580.rpc.client;

import io.github.tong12580.rpc.common.message.ResultMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * <p>RpcNettyClientHandler</p>
 * <span></span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/23 16:48
 */
public class RpcNettyClientHandler extends SimpleChannelInboundHandler<ResultMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResultMessage msg) throws Exception {

    }
}
