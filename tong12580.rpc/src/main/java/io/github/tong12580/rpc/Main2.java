package io.github.tong12580.rpc;

import io.github.tong12580.rpc.client.RpcClient;

/**
 * Main2
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-25 12:38
 */
public class Main2 {
    public static void main(String[] args) {
        RpcClient re =
        new RpcClient(8080, "127.0.0.1", 1);
        re.run();
    }
}
