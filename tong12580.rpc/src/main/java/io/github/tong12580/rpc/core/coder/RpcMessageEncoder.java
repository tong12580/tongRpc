package io.github.tong12580.rpc.core.coder;

import io.github.tong12580.rpc.common.SerializerUtils;
import io.github.tong12580.rpc.common.message.ResultMessage;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * <p>RpcMessageEncoder</p>
 * <span>消息编码器</span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/22 23:19
 */
@Sharable
public class RpcMessageEncoder extends MessageToMessageEncoder<ResultMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ResultMessage msg, List<Object> out) throws Exception {
        out.add(SerializerUtils.serializer(msg));
    }
}
