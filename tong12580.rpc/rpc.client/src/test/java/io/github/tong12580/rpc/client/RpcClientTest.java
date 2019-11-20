package io.github.tong12580.rpc.client;

import org.junit.Test;

/**
 * <p>RpcClientTest</p>
 * <span></span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/25 21:03
 */
public class RpcClientTest {

    @Test
    public void testClient() {
        RpcClient re = new RpcClient(8080, "127.0.0.1", 1);
        re.run();
    }
}
