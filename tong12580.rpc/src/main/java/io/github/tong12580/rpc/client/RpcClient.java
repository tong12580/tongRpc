package io.github.tong12580.rpc.client;

import io.github.tong12580.rpc.core.coder.RpcMessageDecoder;
import io.github.tong12580.rpc.core.coder.RpcMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>RpcClient</p>
 * <span></span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/23 16:31
 */
@Slf4j
public class RpcClient extends Thread {
    private int port;
    private String host;
    private int clientLoopSize;

    public RpcClient(int port, String host, int clientLoopSize) {
        super("RpcClient");
        this.port = port;
        this.host = host;
        this.clientLoopSize = clientLoopSize;
    }

    private class ClientSocketChannelInitializer<T extends Channel> extends ChannelInitializer {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline()
                    .addLast(new ProtobufVarint32FrameDecoder())
                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                    .addLast(new RpcMessageDecoder())
                    .addLast(new RpcNettyClientHandler())
                    .addLast(new RpcMessageEncoder())
            ;
        }
    }

    @Override
    public void run() {
        EventLoopGroup eventExecutors = new NioEventLoopGroup(clientLoopSize);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .group(eventExecutors)
                .handler(new ClientSocketChannelInitializer());
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                SocketChannel socketChannel = (SocketChannel) future.channel();
                log.info("----------------connect server success {}----------------", socketChannel.localAddress());
                socketChannel.closeFuture().sync();
            }
        } catch (InterruptedException e) {
            log.error("RpcNettyClient error", e);
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
