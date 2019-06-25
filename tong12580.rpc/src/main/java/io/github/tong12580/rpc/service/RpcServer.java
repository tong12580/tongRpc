package io.github.tong12580.rpc.service;

import io.github.tong12580.rpc.common.SerializerUtils;
import io.github.tong12580.rpc.core.coder.RpcServiceMessageDecoder;
import io.github.tong12580.rpc.core.coder.RpcServiceMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * rpc服务
 * RpcServer
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-20 15:04
 */
@Slf4j
public class RpcServer extends Thread {

    private int port;
    private int workerGroupThreadsSize;
    private int bossGroupThreadsSize;

    public RpcServer(int port, int workerGroupThreadsSize, int bossGroupThreadsSize) {
        super("RpcServer");
        this.port = port;
        this.workerGroupThreadsSize = workerGroupThreadsSize;
        this.bossGroupThreadsSize = bossGroupThreadsSize;
    }

    @Override
    public void run() {
        log.info("Netty server start, port is : {}", port);
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupThreadsSize);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupThreadsSize);
        ServerBootstrap b = new ServerBootstrap();
        b = b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline()
                                .addLast(new ProtobufVarint32FrameDecoder())
                                .addLast(new ProtobufVarint32LengthFieldPrepender())
                                .addLast(new RpcServiceMessageDecoder())
                                .addLast(new RpcServiceMessageEncoder())
                                .addLast(new RpcNettyServerHandler(UUID.randomUUID().toString()))
                                .addLast(new IdleStateHandler(61, 0, 0, TimeUnit.SECONDS))
                        ;
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture future = b.bind(port).sync();
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.info("----------------Server start success {}----------------",
                            channelFuture.channel().localAddress());
                } else {
                    log.error("Server start attempt failed!");
                    channelFuture.cause().printStackTrace();
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("NettyServer error", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            SerializerUtils.cleanBuffer();
        }
    }
}
