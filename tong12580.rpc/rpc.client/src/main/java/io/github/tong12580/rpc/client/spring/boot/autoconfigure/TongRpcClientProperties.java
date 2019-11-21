package io.github.tong12580.rpc.client.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * TongRpcClientProperties
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-11-21 11:19
 */
@ConfigurationProperties(
        prefix = "tong.rpc.client"
)
public class TongRpcClientProperties {
    private String ip = "127.0.0.1";
    private int port = 21570;
    private int clientLoopSize = Runtime.getRuntime().availableProcessors() * 2;

    public int getClientLoopSize() {
        return clientLoopSize;
    }

    public void setClientLoopSize(int clientLoopSize) {
        this.clientLoopSize = clientLoopSize;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
