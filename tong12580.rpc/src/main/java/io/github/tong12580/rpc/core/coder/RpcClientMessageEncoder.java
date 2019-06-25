package io.github.tong12580.rpc.core.coder;

import io.github.tong12580.rpc.common.SerializerUtils;
import io.github.tong12580.rpc.common.message.RequestMessage;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 消息服务端编码器
 * RpcClientMessageEncoder
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-25 11:05
 */
@Sharable
public class RpcClientMessageEncoder extends MessageToMessageEncoder<RequestMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage msg, List<Object> out) throws Exception {
        out.add(SerializerUtils.serializer(msg));
    }
}
