package io.github.tong12580.rpc.service;

import com.alibaba.fastjson.JSON;
import io.github.tong12580.rpc.common.message.RequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Rpc服务处理器
 * RpcNettyServerHandler
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-21 14:44
 */
@Slf4j
public class RpcNettyServerHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        log.info("message {}", JSON.toJSONString(msg));
    }
}
