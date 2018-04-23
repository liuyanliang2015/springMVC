package com.bert.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtobuffSerializationUtil {
	 private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();  
	    private static Objenesis objenesis = new ObjenesisStd(true);  
	      
	    public static<T> Schema<T> getSchema(Class<T> clazz){  
	        @SuppressWarnings("unchecked")  
	        Schema<T> schema = (Schema<T>) cachedSchema.get(clazz);  
	        if (schema == null) {  
	        	 schema = RuntimeSchema.getSchema(clazz);  
	            if (schema != null) {  
	                cachedSchema.put(clazz, schema);  
	            }  
	        }  
	        return schema;  
	    }  
	      
	    /** 
	     * 序列化 
	     * 
	     * @param obj 
	     * @return 
	     */  
	    public static <T> byte[] serialize(T obj) {  
	        @SuppressWarnings("unchecked")  
	        Class<T> clazz = (Class<T>) obj.getClass();  
	        LinkedBuffer buffer = LinkedBuffer  
	                .allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);  
	        try {  
	            Schema<T> schema = getSchema(clazz);  
	            return ProtobufIOUtil.toByteArray(obj, schema, buffer);  
	        } catch (Exception e) {  
	            throw new IllegalStateException(e.getMessage(), e);  
	        } finally {  
	            buffer.clear();  
	        }  
	    }  
	    /** 
	     * 序列化List 
	     * 
	     * @param obj 
	     * @return 
	     */  
	    public static <T> byte[] serializeList(List<T> objList) {  
	        if(objList == null || objList.isEmpty()){  
	            throw new RuntimeException("序列化对象列表("+objList+")参数异常！");  
	        }  
	        @SuppressWarnings("unchecked")  
	        Class<T> clazz = (Class<T>) objList.get(0).getClass();  
	        LinkedBuffer buffer = LinkedBuffer  
	                .allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);  
	        byte[] protostuff = null;  
	        ByteArrayOutputStream bos = null;  
	        try {  
	            bos = new ByteArrayOutputStream();  
	            Schema<T> schema = getSchema(clazz);  
	            ProtostuffIOUtil.writeListTo(bos,objList,schema,buffer);  
	            protostuff = bos.toByteArray();  
	            return protostuff;  
	        } catch (Exception e) {  
	            throw new IllegalStateException(e.getMessage(), e);  
	        } finally {  
	            buffer.clear();  
	        }  
	    }  
	    /** 
	     * 反序列化 
	     * 
	     * @param data 
	     * @param clazz 
	     * @return 
	     */  
	    public static <T> T deserialize(byte[] data, Class<T> clazz) {  
	        try {  
	            T obj = objenesis.newInstance(clazz);  
	            Schema<T> schema = getSchema(clazz);  
	            ProtobufIOUtil.mergeFrom(data, obj, schema);  
	            return obj;  
	        } catch (Exception e) {  
	            throw new IllegalStateException(e.getMessage(), e);  
	        }  
	    }  
	    /** 
	     * 反序列化List 
	     * 
	     * @param data 
	     * @param clazz 
	     * @return 
	     */  
	    public static <T> List<T> deserializeList(byte[] data, Class<T> clazz) {  
	        if(data == null || data.length == 0){  
	            throw new RuntimeException("反序列化对象发生异常，byte序列为空！");  
	        }  
	        try {  
	            Schema<T> schema = getSchema(clazz);  
	            List<T> result = null;  
	            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(data),schema);  
	            return result;  
	        } catch (Exception e) {  
	            throw new IllegalStateException(e.getMessage(), e);  
	        }  
	    }      
}
