package io.github.tong12580.common.core.lang;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * <p>SerializerUtils</p>
 * <span></span>
 *
 * @author yuTong
 * @version 1.0
 * @since 2019/6/22 22:27
 */
public class SerializerUtils {
    private static final ThreadLocal<LinkedBuffer> BUFFERS = new ThreadLocal<>();

    public static <T> byte[] serializer(T t) {
        LinkedBuffer buffer = BUFFERS.get();
        if (null == buffer) {
            buffer = LinkedBuffer.allocate();
            BUFFERS.set(buffer);
        }
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(t.getClass());
        byte[] bytes = ProtobufIOUtil.toByteArray(t, schema, buffer);
        buffer.clear();
        return bytes;
    }

    public static <T> T deserializer(byte[] bytes, Class<T> tClass) {
        Schema<T> schema = RuntimeSchema.getSchema(tClass);
        T t = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, t, schema);
        return t;
    }

    public static void cleanBuffer() {
        LinkedBuffer buffer = BUFFERS.get();
        if (null != buffer) {
            buffer.clear();
        }
        BUFFERS.remove();
    }

}
