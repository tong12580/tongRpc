package io.github.tong12580.common.core.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TongRpcProperties
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-11-21 13:49
 */
@ConfigurationProperties(
        prefix = "tong"
)
public class TongRpcProperties {

    private boolean checkConfigLocation = false;
    private String configLocation;

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public boolean isCheckConfigLocation() {
        return checkConfigLocation;
    }

    public void setCheckConfigLocation(boolean checkConfigLocation) {
        this.checkConfigLocation = checkConfigLocation;
    }
}
