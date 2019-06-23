package io.github.tong12580.rpc.core.coder;

import io.github.tong12580.rpc.common.SerializerUtils;
import io.github.tong12580.rpc.common.message.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * <p>RpcMessageDecoder</p>
 * <span></span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/22 22:50
 */
@Sharable
public class RpcMessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        RequestMessage requestMessage = SerializerUtils.deserializer(
                ByteBufUtil.getBytes(msg, msg.readerIndex(), msg.readableBytes(), false), RequestMessage.class);
        out.add(requestMessage);
    }
}
