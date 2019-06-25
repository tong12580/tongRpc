package io.github.tong12580.rpc.client;

import com.alibaba.fastjson.JSON;
import io.github.tong12580.rpc.common.message.RequestMessage;
import io.github.tong12580.rpc.common.message.ResultMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>RpcNettyClientHandler</p>
 * <span></span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/23 16:48
 */
@Slf4j
public class RpcNettyClientHandler extends SimpleChannelInboundHandler<ResultMessage> {

    private Channel channel;

    Channel getChannel() {
        return channel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResultMessage msg) throws Exception {
        log.info("msg {}", JSON.toJSON(msg));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.channel = ctx.channel();
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setPing("ping");
        ctx.writeAndFlush(requestMessage);
    }
}
