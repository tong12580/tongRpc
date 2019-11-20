package io.github.tong12580.common.core.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 服务发现
 * ConsulUtil
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-11-20 14:32
 */
public class ConsulUtil {
    private ConsulClient consulClient;

    public ConsulUtil(String consulIp, int consulPort) {
        this.consulClient = new ConsulClient(consulIp, consulPort);
    }

    /**
     * 注册服务
     */
    public void registerService(String serviceName, String serviceId, String ip, Integer port, String interval,
                                String timeout, String... tags) {
        // register new service
        NewService newService = new NewService();
        newService.setId(serviceId);
        newService.setName(serviceName);
        newService.setTags(Arrays.asList(tags));
        newService.setPort(port);
        newService.setAddress(ip);

        NewService.Check serviceCheck = new NewService.Check();
        serviceCheck.setTcp(ip + ":" + port);
        serviceCheck.setInterval(StringUtils.isBlank(interval) ? "10s" : interval);
        serviceCheck.setTimeout(StringUtils.isBlank(timeout) ? "1s" : timeout);
        newService.setCheck(serviceCheck);
        consulClient.agentServiceRegister(newService);
    }

    public List<HealthService> findHealthyService(String name) {
        Response<List<HealthService>> healthyServices = consulClient.getHealthServices(name,
                HealthServicesRequest.newBuilder().setPassing(true).setQueryParams(QueryParams.DEFAULT).build());
        return healthyServices.getValue();
    }

    public void storeKeyAndValue(String key, String value) {
        consulClient.setKVValue(key, value);
    }

    public String getValue(String key) {
        return consulClient.getKVValue(key).getValue().getDecodedValue();
    }

    public List<String> findRaftPeers() {
        Response<List<String>> listResponse = consulClient.getStatusPeers();
        return listResponse.getValue();
    }

    public String findRaftLeader() {
        Response<String> stringResponse = consulClient.getStatusLeader();
        return stringResponse.getValue();
    }
}
