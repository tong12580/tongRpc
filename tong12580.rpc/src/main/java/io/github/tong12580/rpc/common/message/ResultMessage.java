package io.github.tong12580.rpc.common.message;

import com.alibaba.fastjson.JSON;

/**
 * <p>ResultMessage</p>
 * <span></span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/22 23:07
 */
public class ResultMessage {
    private String serverId;
    private Long requestId;
    private byte[] result;
    private Class aClass;
    private String pong;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Object getBody() {
        if (null != result && result.length > 0) {
            return JSON.parseObject(result, aClass);
        }
        return null;
    }

    public String getPong() {
        return pong;
    }

    public void setPong(String pong) {
        this.pong = pong;
    }

    public void setBody(Object t) {
        this.aClass = t.getClass();
        this.result = JSON.toJSONBytes(result);
    }
}
