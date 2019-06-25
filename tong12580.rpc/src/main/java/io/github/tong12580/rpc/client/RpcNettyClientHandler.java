package io.github.tong12580.rpc.client;

import com.alibaba.fastjson.JSON;
import io.github.tong12580.rpc.common.Constants;
import io.github.tong12580.rpc.common.message.RequestMessage;
import io.github.tong12580.rpc.common.message.ResultMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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
    private String clientId;

    public RpcNettyClientHandler(String clientId) {
        this.clientId = clientId;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResultMessage msg) throws Exception {
        try {
            log.info("msg {}", JSON.toJSON(msg));
        } catch (Exception e) {
            log.error("ee", e);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("Client circular heartbeat monitoring sends: {}, id {}", System.currentTimeMillis(), clientId);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                RequestMessage requestMessage = new RequestMessage();
                requestMessage.setPing(Constants.PING);
                requestMessage.setClientId(clientId);
                ctx.writeAndFlush(requestMessage);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Rpc client exception ! ctx id is " + ctx.channel().id().asLongText(), cause);
        ctx.close();
    }
}
