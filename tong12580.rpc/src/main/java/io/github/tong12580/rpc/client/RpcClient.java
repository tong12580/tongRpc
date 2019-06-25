package io.github.tong12580.rpc.client;

import io.github.tong12580.rpc.core.coder.RpcClientMessageDecoder;
import io.github.tong12580.rpc.core.coder.RpcClientMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    private Channel channel;

    public Channel getChannel() {
        if (null != channel) {
            return channel;
        }
        return null;
    }

    public RpcClient(int port, String host, int clientLoopSize) {
        super("RpcClient");
        this.port = port;
        this.host = host;
        this.clientLoopSize = clientLoopSize;
    }

    @Override
    public void run() {
        log.info("Netty client start, port is : {}", port);
        EventLoopGroup eventExecutors = new NioEventLoopGroup(clientLoopSize);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .group(eventExecutors)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ProtobufVarint32FrameDecoder())
                                .addLast(new ProtobufVarint32LengthFieldPrepender())
                                .addLast(new RpcClientMessageDecoder())
                                .addLast(new RpcClientMessageEncoder())
                                .addLast(new IdleStateHandler(0, 1, 0, TimeUnit.MINUTES))
                                .addLast(new RpcNettyClientHandler(UUID.randomUUID().toString()))
                        ;
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    SocketChannel socketChannel = (SocketChannel) channelFuture.channel();
                    log.info("----------------connect server success {}----------------", socketChannel.localAddress());
                    this.channel = socketChannel;
                } else {
                    log.error("Connect server attempt failed!");
                    channelFuture.cause().printStackTrace();
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("RpcNettyClient error", e);
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
