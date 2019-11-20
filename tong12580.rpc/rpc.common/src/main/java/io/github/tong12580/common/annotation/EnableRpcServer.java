package io.github.tong12580.common.annotation;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * <p>EnableRpcServer</p>
 * <span>对外提供RPC服务标签</span>
 *
 * <span>被该标记标标注的将成为Rpc客户端, 具有提供Rpc服务能力</span>
 * 我们会根据这个注解为其生成一个RpcServer服务. 并寻找其指定的项目包下的相关 {@link RpcApi} 注解标记过的方法或者接口,
 * 为他们生成动态代理, 传递调用并返回调用结果.
 *
 * What is marked by this tag becomes an Rpc client with the ability to provide Rpc services.
 * We will generate an RpcServer service based on this annotation, and look for the method or interface
 * marked by {@link RpcApi} annotation under its specified project package.
 * Generate dynamic proxies for them, pass the call and return the result of the call.
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/25 21:37
 */
@Documented
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RpcServerScannerRegistrar.class)
public @interface EnableRpcServer {

    /**
     * 扫描包位置
     * Scanning packet position
     */
    String[] basePackages() default {};

}
