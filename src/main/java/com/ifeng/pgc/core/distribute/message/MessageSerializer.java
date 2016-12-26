package com.ifeng.pgc.core.distribute.message;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.ifeng.pgc.utils.SchemaUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by zhanglr on 2016/8/28.
 */
public class MessageSerializer<T> {
    public static <T> ByteBuf serialize(T t){
        try{
            Schema<T> schema = (Schema<T>)SchemaUtil.getSchema(t.getClass());
            LinkedBuffer buffer = LinkedBuffer.allocate(1024);
            byte[] bs = ProtostuffIOUtil.toByteArray(t,schema,buffer);
            return Unpooled.copiedBuffer(bs);
        }catch (Exception e){}
        return null;
    }
}
