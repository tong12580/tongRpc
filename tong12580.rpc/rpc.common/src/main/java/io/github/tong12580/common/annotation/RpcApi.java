package io.github.tong12580.common.annotation;

import io.github.tong12580.common.core.spring.MapperFactoryBean;
import org.springframework.beans.factory.support.BeanNameGenerator;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * <p>RpcApi</p>
 * <span>Rpc接口</span>
 *
 * <span>被该标记标标注的将成为Rpc接口, 具有Rpc相关调用能力</span>
 * 该注解标记后, 只有在开启了 {@link EnableRpcServer} 或者 {@link EnableRpcClient}的 服务才能生效
 * 这时, api才会在项目初始化话时, 根据对应服务动态生成对应的代理.
 * <p>
 * What is marked by this tag becomes an Rpc interface with Rpc related calling capabilities
 * After this annotation mark, a service of {@link EnableRpcServer} or {@link EnableRpcClient} is enabled,
 * At this point, the API dynamically generates the corresponding proxy based on the corresponding service
 * when the project initializes.
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/25 21:39
 */
@Documented
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcApi {

    /**
     * 是否是服务提供方
     * Is it the service provider
     */
    boolean isServerProvider() default false;

    /**
     * 扫描包位置
     * Scanning packet position
     */
    String[] basePackages() default {};

    /**
     * The {@link BeanNameGenerator} class to be used for naming detected components
     * within the Spring container.
     */
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;


    /**
     * This property specifies the annotation that the scanner will search for.
     * <p>
     * The scanner will register all interfaces in the base package that also have
     * the specified annotation.
     * <p>
     * Note this can be combined with markerInterface.
     */
    Class<? extends Annotation> annotationClass() default Annotation.class;

    /**
     * This property specifies the parent that the scanner will search for.
     * <p>
     * The scanner will register all interfaces in the base package that also have
     * the specified interface class as a parent.
     * <p>
     * Note this can be combined with annotationClass.
     */
    Class<?> markerInterface() default Class.class;

    /**
     * Specifies a custom MapperFactoryBean to return a mybatis proxy as spring bean.
     *
     */
    Class<? extends MapperFactoryBean> factoryBean() default MapperFactoryBean.class;
}
