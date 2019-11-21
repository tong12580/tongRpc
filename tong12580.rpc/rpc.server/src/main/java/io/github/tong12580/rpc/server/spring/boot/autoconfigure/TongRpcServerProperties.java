package io.github.tong12580.rpc.server.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TongRpcServerProperties
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-11-21 11:26
 */
@ConfigurationProperties(
        prefix = "tong.rpc.server"
)
public class TongRpcServerProperties {

    private int port = 9051;

    private int workerGroupThreadsSize = Runtime.getRuntime().availableProcessors() * 2;
    private int bossGroupThreadsSize = Runtime.getRuntime().availableProcessors() * 2;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWorkerGroupThreadsSize() {
        return workerGroupThreadsSize;
    }

    public void setWorkerGroupThreadsSize(int workerGroupThreadsSize) {
        this.workerGroupThreadsSize = workerGroupThreadsSize;
    }

    public int getBossGroupThreadsSize() {
        return bossGroupThreadsSize;
    }

    public void setBossGroupThreadsSize(int bossGroupThreadsSize) {
        this.bossGroupThreadsSize = bossGroupThreadsSize;
    }
}
