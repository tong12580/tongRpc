package io.githud.tong12580.rpc.server;

import io.github.tong12580.common.acquiescent.RpcDefaultConfig;
import io.github.tong12580.common.cache.PortTableCache;
import io.github.tong12580.common.core.coder.RpcServiceMessageDecoder;
import io.github.tong12580.common.core.coder.RpcServiceMessageEncoder;
import io.github.tong12580.common.core.lang.DefaultThreadFactory;
import io.github.tong12580.common.core.lang.SerializerUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * rpc服务
 * RpcServer
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-20 15:04
 */
@Slf4j
public class RpcServer extends Thread implements Supplier<RpcServer> {

    private int port;
    private int workerGroupThreadsSize;
    private int bossGroupThreadsSize;
    private Channel channel;
    private CountDownLatch cyclicBarrier;

    public Channel getChannel() {
        return channel;
    }

    private static ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1),
            new DefaultThreadFactory("RpcServer"));


    public RpcServer(int port, int workerGroupThreadsSize, int bossGroupThreadsSize, CountDownLatch cyclicBarrier) {
        super("RpcServer");
        this.port = port;
        this.workerGroupThreadsSize = workerGroupThreadsSize;
        this.bossGroupThreadsSize = bossGroupThreadsSize;
        this.cyclicBarrier = cyclicBarrier;
    }

    public RpcServer(CountDownLatch cyclicBarrier) {
        super("RpcServer");
        RpcDefaultConfig config = RpcDefaultConfig.build();
        int port = config.getRpc().getServer().getPort();
        while (PortTableCache.containsKey(port)) {
            port++;
        }
        this.workerGroupThreadsSize = config.getRpc().getServer().getWorkerGroupThreadsSize();
        this.bossGroupThreadsSize = config.getRpc().getServer().getBossGroupThreadsSize();
        this.cyclicBarrier = cyclicBarrier;
    }

    public RpcServer(int port) {
        super("RpcServer");
        this.port = port;
        this.workerGroupThreadsSize = Runtime.getRuntime().availableProcessors() * 2;
        this.bossGroupThreadsSize = Runtime.getRuntime().availableProcessors() * 2;
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
                    this.channel = channelFuture.channel();
                    cyclicBarrier.countDown();
                } else {
                    log.error("Server start attempt failed!", channelFuture.cause());
                    port++;
                    channelFuture.channel()
                            .eventLoop()
                            .schedule(new RpcServer(port), 10, TimeUnit.SECONDS);
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

    @Override
    public RpcServer get() {
       return builder();
    }

    public static RpcServer builder() {
        CountDownLatch cyclicBarrier = new CountDownLatch(1);
        RpcServer server = new RpcServer(cyclicBarrier);
        executorService.execute(server);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            log.error("Wait for an exception when getting RPC", e);
        }
        return server;
    }
}
