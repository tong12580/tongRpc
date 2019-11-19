package io.github.tong12580.common.acquiescent;

import org.springframework.beans.factory.FactoryBean;
import org.yaml.snakeyaml.Yaml;

/**
 * Rpc默认配置
 * RpcDefaultConfig
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-28 14:11
 */
public class RpcDefaultConfig implements FactoryBean<RpcDefaultConfig> {

    private static final String DEFAULT_CONFIG_PATH = "/rpc-default.yml";

    /**
     * rpc : {"client":{"port":21570},"server":{"port":15860}}
     */

    private RpcBean rpc;

    public RpcBean getRpc() {
        return rpc;
    }

    public void setRpc(RpcBean rpc) {
        this.rpc = rpc;
    }

    @Override
    public RpcDefaultConfig getObject() throws Exception {
        return build();
    }

    @Override
    public Class<RpcDefaultConfig> getObjectType() {
        return RpcDefaultConfig.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static class RpcBean {
        /**
         * client : {"port":21570}
         * server : {"port":15860}
         */

        private ClientBean client;
        private ServerBean server;

        public ClientBean getClient() {
            return client;
        }

        public void setClient(ClientBean client) {
            this.client = client;
        }

        public ServerBean getServer() {
            return server;
        }

        public void setServer(ServerBean server) {
            this.server = server;
        }

        public static class ClientBean {

            private Integer port;
            private Integer workerGroupThreadsSize;
            private Integer bossGroupThreadsSize;

            public Integer getWorkerGroupThreadsSize() {
                return workerGroupThreadsSize;
            }

            public void setWorkerGroupThreadsSize(Integer workerGroupThreadsSize) {
                this.workerGroupThreadsSize = workerGroupThreadsSize;
            }

            public Integer getBossGroupThreadsSize() {
                return bossGroupThreadsSize;
            }

            public void setBossGroupThreadsSize(Integer bossGroupThreadsSize) {
                this.bossGroupThreadsSize = bossGroupThreadsSize;
            }

            public Integer getPort() {
                return port;
            }

            public void setPort(Integer port) {
                this.port = port;
            }
        }

        public static class ServerBean {
            /**
             * port : 15860
             */

            private Integer port;
            private Integer workerGroupThreadsSize = Runtime.getRuntime().availableProcessors() * 2;
            private Integer bossGroupThreadsSize = Runtime.getRuntime().availableProcessors() * 2;

            public Integer getWorkerGroupThreadsSize() {
                return workerGroupThreadsSize;
            }

            public Integer getBossGroupThreadsSize() {
                return bossGroupThreadsSize;
            }

            public Integer getPort() {
                return port;
            }

            public void setPort(Integer port) {
                this.port = port;
            }
        }
    }

    public static RpcDefaultConfig build() {
        Yaml yaml = new Yaml();
        return yaml.loadAs(RpcDefaultConfig.class.getResourceAsStream(DEFAULT_CONFIG_PATH), RpcDefaultConfig.class);
    }

}
