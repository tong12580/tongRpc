package io.github.tong12580.rpc.core.spring.annotation;

import io.github.tong12580.rpc.common.acquiescent.RpcDefaultConfig;
import io.github.tong12580.rpc.core.lang.EnableRpcServer;
import io.github.tong12580.rpc.service.RpcServer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Rpc注解扫描
 * RpcServerScannerRegistrar
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-27 16:35
 */
public class RpcServerScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata
                .getAnnotationAttributes(EnableRpcServer.class.getName()));

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
