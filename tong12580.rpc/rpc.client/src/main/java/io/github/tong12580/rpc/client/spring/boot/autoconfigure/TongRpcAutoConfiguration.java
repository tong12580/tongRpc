package io.github.tong12580.rpc.client.spring.boot.autoconfigure;

import io.github.tong12580.common.core.consul.ConsulUtil;
import io.github.tong12580.common.core.spring.MapperFactoryBean;
import io.github.tong12580.common.core.spring.boot.autoconfigure.TongRpcConsulProperties;
import io.github.tong12580.common.core.spring.boot.autoconfigure.TongRpcProperties;
import io.github.tong12580.rpc.client.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

/**
 * RPC自动加载类
 * TongRpcAutoConfiguration
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-11-21 11:15
 */
@Configuration
@EnableConfigurationProperties({TongRpcClientProperties.class, TongRpcConsulProperties.class, TongRpcProperties.class})
public class TongRpcAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(TongRpcAutoConfiguration.class);
    private final ResourceLoader resourceLoader;
    private final TongRpcClientProperties rpcClientProperties;
    private final TongRpcConsulProperties consulProperties;
    private final TongRpcProperties rpcProperties;

    public TongRpcAutoConfiguration(ResourceLoader resourceLoader, TongRpcClientProperties rpcClientProperties,
                                    TongRpcConsulProperties consulProperties, TongRpcProperties rpcProperties) {
        this.resourceLoader = resourceLoader;
        this.rpcClientProperties = rpcClientProperties;
        this.consulProperties = consulProperties;
        this.rpcProperties = rpcProperties;
    }

    @PostConstruct
    public void checkConfigFileExists() {
        if (this.rpcProperties.isCheckConfigLocation() && StringUtils.hasText(this.rpcProperties.getConfigLocation())) {
            Resource resource = this.resourceLoader.getResource(this.rpcProperties.getConfigLocation());
            Assert.state(resource.exists(), "Cannot find config location: " + resource +
                    " (please add config file or check your Mybatis configuration)");
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public ConsulUtil consulUtil() {
        return new ConsulUtil(this.consulProperties.getIp(), this.consulProperties.getPort());
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcClient rpcServer() {
        return new RpcClient(this.rpcClientProperties.getPort(),
                this.rpcClientProperties.getIp(), Runtime.getRuntime().availableProcessors() * 2);
    }

    @Configuration
    @ConditionalOnMissingBean({MapperFactoryBean.class})
    public static class RpcServerScannerRegistrarNotFoundConfiguration {
        public RpcServerScannerRegistrarNotFoundConfiguration() {
        }

        public void afterPropertiesSet() {
            logger.debug("No {} found.", MapperFactoryBean.class.getName());
        }
    }

}
