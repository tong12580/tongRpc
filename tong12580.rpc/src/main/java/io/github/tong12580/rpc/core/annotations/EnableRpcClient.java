package io.github.tong12580.rpc.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * <p>EnableRpcClient</p>
 * <span>Rpc客户端</span>
 *
 * <span>被该标记标标注的将成为Rpc客户端, 具有Rpc相关调用能力</span>
 *
 * 在项目启动时, 声明过这个注解, 并且指定扫描了相关的 api 接口的包位置时,
 * 会为对应的{@link RpcApi}接口生成RpcClient,或者为整个服务生成一个RpcClient;
 * [当然这将根据 {@link EnableRpcClient} 的 {basePackages() } 方法来 来决定] ;
 *
 *
 * What is marked by this tag will become an Rpc client with rpc-related calling capabilities.
 * This annotation is declared when the project starts and the package location is specified for
 * the associated API interface to be scanned; RpcClient is generated for the corresponding {@link RpcApi} interface,
 * or for the entire service; [of course this will be determined by {basePackages()} method {@link EnableRpcClient}];
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/25 21:39
 */
@Documented
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableRpcClient {

    /**
     * 扫描包位置
     * Scanning packet position
     */
    String[] basePackages();

    /**
     * 是否以扫描包的数量建立客户端
     * Whether to set up the client with the number of scanned packets ?
     */
    boolean clientNumIsPackagesSize() default false;

}
