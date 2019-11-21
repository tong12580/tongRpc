package io.github.tong12580.common.core.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

/**
 * TongRpcConsulProperties
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-11-21 11:29
 */
@ConfigurationProperties(
        prefix = "tong.consul"
)
public class TongRpcConsulProperties {

    private String ip = "127.0.0.1";
    private int port = 8500;

    private String serviceName;
    private String serviceId;
    /**
     * 心跳间隔
     * Heartbeat interval
     */
    private String interval;
    private String timeout;
    private String[] tags;

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
