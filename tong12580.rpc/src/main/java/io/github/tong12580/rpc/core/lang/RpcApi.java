package io.github.tong12580.rpc.core.lang;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * <p>RpcApi</p>
 * <span>Rpc接口</span>
 *
 * <span>被该标记标标注的将成为Rpc接口, 具有Rpc相关调用能力</span>
 * 该注解标记后, 只有在开启了 {@link EnableRpcServer} 或者 {@link EnableRpcClient}的 服务才能生效
 * 这时, api才会在项目初始化话时, 根据对应服务动态生成对应的代理.
 *
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
}
