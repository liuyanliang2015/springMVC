package com.bert.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.bert.util.ProtobuffSerializationUtil;

@Service("redisService")  
public class RedisService {
	 private static final Logger log = LoggerFactory.getLogger(RedisService.class); 
	    @Autowired  
	    private ShardedJedisPool shardedJedisPool;  
	      
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 设置单个值 
	     */  
	    public String set(String key,Object value){  
	        String result = null;  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return result;  
	        }  
	        try {  
	            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serialize(value),"ISO-8859-1"));  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return result;  
	    }  
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 设置List 
	     */  
	    @SuppressWarnings({ "unchecked", "rawtypes" })  
	    public String setList(String key,List value){  
	        String result = null;  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return result;  
	        }  
	        try {  
	            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serializeList(value),"ISO-8859-1"));  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return result;  
	    }     
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 设置单个值和有效时间 
	     */  
	    public String set(String key,Object value,int seconds){  
	        String result = null;  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return result;  
	        }  
	        try {  
	            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serialize(value),"ISO-8859-1"));  
	            expire(key, seconds);  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return result;  
	    }     
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 设置List和有效时间 
	     */  
	    @SuppressWarnings({ "unchecked", "rawtypes" })  
	    public String setList(String key,List value,int seconds){  
	        String result = null;  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return result;  
	        }  
	        try {  
	            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serializeList(value),"ISO-8859-1"));  
	            expire(key, seconds);  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return result;  
	    }     
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 获取单个值 
	     */  
	    public <T> T get(String key,Class<T> clazz){  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return null;  
	        }  
	        try {  
	            String resultStr = shardedJedis.get(key);  
	            if(StringUtils.isEmpty(resultStr))  
	                return null;  
	            return ProtobuffSerializationUtil.deserialize(resultStr.getBytes("ISO-8859-1"), clazz);  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	            e.printStackTrace();  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return null;  
	    }     
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 获取List 
	     */  
	    public <T> List<T> getList(String key,Class<T> clazz){  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return null;  
	        }  
	        try {  
	            String resultStr = shardedJedis.get(key);  
	            if(StringUtils.isEmpty(resultStr))  
	                return null;  
	            return ProtobuffSerializationUtil.deserializeList(resultStr.getBytes("ISO-8859-1"), clazz);  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	            e.printStackTrace();  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return null;  
	    }     
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 判断key是否存在 
	     */  
	    public Boolean exists(String key){  
	        Boolean result = false;  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return result;  
	        }  
	        try {  
	            result = shardedJedis.exists(key);  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return result;  
	    }  
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 设置key的过期时间段 
	     */  
	    public Long expire(String key,int seconds){  
	        Long result = null;  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return result;  
	        }  
	        try {  
	            result = shardedJedis.expire(key, seconds);  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return result;  
	    }     
	    /** 
	     * @param key 
	     * @param value 
	     * @return 
	     * 设置key的过期时间点 
	     */  
	    public Long expire(String key,long unixTime){  
	        Long result = null;  
	        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
	        if(shardedJedis == null){  
	            return result;  
	        }  
	        try {  
	            result = shardedJedis.expireAt(key, unixTime);  
	        } catch (Exception e) {  
	            log.error(e.getMessage(),e);  
	        } finally{  
	        	shardedJedisPool.returnResource(shardedJedis);
	        }  
	        return result;  
	    }     
	      
}
