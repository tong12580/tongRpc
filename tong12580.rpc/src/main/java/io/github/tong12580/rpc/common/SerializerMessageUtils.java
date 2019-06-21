package io.github.tong12580.rpc.common;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 信息的序列化
 * SerializerMessageUtils
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-6-21 16:25
 */
public class SerializerMessageUtils {
    private static final ThreadLocal<LinkedBuffer> BUFFERS = new ThreadLocal<>();

    public static byte[] serializer(Message message) {
        LinkedBuffer buffer = BUFFERS.get();
        if (null == buffer) {
            buffer = LinkedBuffer.allocate();
            BUFFERS.set(buffer);
        }
        Schema<Message> schema = RuntimeSchema.getSchema(Message.class);
        byte[] bytes = ProtobufIOUtil.toByteArray(message, schema, buffer);
        buffer.clear();
        return bytes;
    }

    /**
     * 简单对象的反序列化
     *
     * @param bytes  字节码
     * @return 对象
     */
    public static Message deserializer(byte[] bytes) {
        Schema<Message> schema = RuntimeSchema.getSchema(Message.class);
        Message t = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, t, schema);
        return t;
    }

    public static void cleanBuffer() {
        BUFFERS.remove();
    }
}
