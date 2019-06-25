package io.github.tong12580.rpc.core.coder;

import io.github.tong12580.rpc.common.SerializerUtils;
import io.github.tong12580.rpc.common.message.ResultMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 消息客户端解码器
 * 协议类型 <span>Protobuf</span>
 * RpcClientMessageDecoder
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-25 11:05
 */
@Sharable
public class RpcClientMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        ResultMessage resultMessage = SerializerUtils.deserializer(
                ByteBufUtil.getBytes(msg, msg.readerIndex(), msg.readableBytes(), false), ResultMessage.class);
        out.add(resultMessage);
    }
}
