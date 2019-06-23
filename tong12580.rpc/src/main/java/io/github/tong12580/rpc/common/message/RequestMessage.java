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
    private Long requestId;
    private String className;
    private String MethodNames;
    private Class<?>[] params;
    private Object[] values;

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
        return MethodNames;
    }

    public void setMethodNames(String methodNames) {
        MethodNames = methodNames;
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
}
