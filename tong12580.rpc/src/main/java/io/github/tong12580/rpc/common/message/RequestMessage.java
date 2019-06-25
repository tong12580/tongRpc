package io.github.tong12580.rpc.common.message;

/**
 * <p>RequestMessage</p>
 * <span>Rpc调用消息</span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/22 22:23
 */
public class RequestMessage {
    private String clientId;
    private Long requestId;
    private String ping;
    private String className;
    private String methodNames;
    private Class<?>[] params;
    private Object[] values;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodNames() {
        return methodNames;
    }

    public void setMethodNames(String methodNames) {
        this.methodNames = methodNames;
    }

    public Class<?>[] getParams() {
        return params;
    }

    public void setParams(Class<?>[] params) {
        this.params = params;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }
}
