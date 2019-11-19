package io.githud.tong12580.rpc.server;

import com.alibaba.fastjson.JSON;
import io.github.tong12580.common.core.lang.Constants;
import io.github.tong12580.common.message.RequestMessage;
import io.github.tong12580.common.message.ResultMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
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
    private int lossConnectCount = 0;
    private String serviceId;

    RpcNettyServerHandler(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        log.info("message {}", JSON.toJSONString(msg));
        if (null == msg.getRequestId()) {
            if (Constants.PING.equals(msg.getPing())) {
                ResultMessage resultMessage = new ResultMessage();
                resultMessage.setPong(Constants.PONG);
                resultMessage.setServerId(serviceId);
                ctx.writeAndFlush(resultMessage);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Rpc service exception ! ctx id is " + ctx.channel().id().asLongText(), cause);
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("The client message has not been received for 5 seconds!");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                lossConnectCount++;
                int inactiveNumber = 2;
                if (lossConnectCount > inactiveNumber) {
                    log.error("Close this inactive channel!");
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
