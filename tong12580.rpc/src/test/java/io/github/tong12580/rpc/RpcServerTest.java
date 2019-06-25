package io.github.tong12580.rpc;

import io.github.tong12580.rpc.service.RpcServer;
import org.junit.Test;

/**
 * <p>RpcServerTest</p>
 * <span></span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/25 21:03
 */
public class RpcServerTest {

    @Test
    public void testServer() {
        RpcServer re = new RpcServer(8080, 1, 1);
        re.run();
    }
}
