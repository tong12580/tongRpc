package io.github.tong12580.rpc.common;

import com.alibaba.fastjson.JSON;

/**
 * 消息
 * Message
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-21 16:20
 */
public class Message<T> {
    private Long messageId;
    private Integer type;
    /**
     * 来源
     */
    private String source;
    /**
     * 消息体
     */
    private byte[] body;
    private Integer code;
    private Class aClass;


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public T getBody() {
        return JSON.parseObject(body, aClass);
    }

    public void setBody(Object body) {
        this.aClass = body.getClass();
        this.body = JSON.toJSONBytes(body);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
