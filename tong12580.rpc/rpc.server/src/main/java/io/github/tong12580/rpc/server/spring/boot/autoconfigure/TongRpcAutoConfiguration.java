package io.github.tong12580.rpc.server.spring.boot.autoconfigure;

import io.github.tong12580.common.core.consul.ConsulUtil;
import io.github.tong12580.common.core.spring.MapperFactoryBean;
import io.github.tong12580.common.core.spring.boot.autoconfigure.TongRpcConsulProperties;
import io.github.tong12580.common.core.spring.boot.autoconfigure.TongRpcProperties;
import io.github.tong12580.rpc.server.RpcServer;
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
@EnableConfigurationProperties({TongRpcServerProperties.class, TongRpcConsulProperties.class, TongRpcProperties.class})
public class TongRpcAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(TongRpcAutoConfiguration.class);
    private final ResourceLoader resourceLoader;
    private final TongRpcServerProperties rpcServerProperties;
    private final TongRpcConsulProperties consulProperties;
    private final TongRpcProperties rpcProperties;


    public TongRpcAutoConfiguration(ResourceLoader resourceLoader, TongRpcServerProperties rpcServerProperties,
                                    TongRpcConsulProperties consulProperties, TongRpcProperties rpcProperties) {
        this.resourceLoader = resourceLoader;
        this.rpcServerProperties = rpcServerProperties;
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
    public RpcServer rpcServer() {
        return new RpcServer(this.rpcServerProperties.getPort(),
                this.rpcServerProperties.getWorkerGroupThreadsSize(),
                this.rpcServerProperties.getBossGroupThreadsSize(), new CountDownLatch(1));
    }

    @Bean
    @ConditionalOnMissingBean
    public ConsulUtil consulUtil() {
        ConsulUtil consulUtil = new ConsulUtil(this.consulProperties.getIp(), this.consulProperties.getPort());
        consulUtil.registerService(this.consulProperties.getServiceName(), this.consulProperties.getServiceId(),
                this.consulProperties.getIp(), this.consulProperties.getPort(), this.consulProperties.getInterval(),
                this.consulProperties.getTimeout(), this.consulProperties.getTags());
        return consulUtil;
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
