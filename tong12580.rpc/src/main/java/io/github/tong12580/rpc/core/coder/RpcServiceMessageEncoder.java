package io.github.tong12580.rpc.core.coder;

import io.github.tong12580.rpc.common.SerializerUtils;
import io.github.tong12580.rpc.common.message.ResultMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * <p>RpcServiceMessageEncoder</p>
 * <span>消息服务编码器</span>
 * 协议类型 <span>Protobuf</span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/22 23:19
 */
@Sharable
public class RpcServiceMessageEncoder extends MessageToMessageEncoder<ResultMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ResultMessage msg, List<Object> out) throws Exception {
        out.add(Unpooled.wrappedBuffer(SerializerUtils.serializer(msg)));
    }
}
