package io.github.tong12580.rpc.service;

import io.github.tong12580.rpc.common.SerializerMessageUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

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
        super("rpcServer");
        this.port = port;
        this.workerGroupThreadsSize = workerGroupThreadsSize;
        this.bossGroupThreadsSize = bossGroupThreadsSize;
    }

    private class ServerSocketChannelInitializer<T extends Channel> extends ChannelInitializer {

        @Override
        protected void initChannel(Channel ch) throws Exception {

        }
    }

    @Override
    public void run() {
        log.info("Netty server start, port is : {}", port);
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupThreadsSize);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupThreadsSize);
        ServerBootstrap b = new ServerBootstrap();
        b = b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerSocketChannelInitializer<SocketChannel>())
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            ChannelFuture future = b.bind(port).sync();
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    log.error("Server bound");
                } else {
                    log.error("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("NettyServer error", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            SerializerMessageUtils.cleanBuffer();
        }
    }
}
