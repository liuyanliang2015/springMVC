package com.bert.redis;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
public class RedisShardPool {
	private static ApplicationContext appContext;  
	private static ShardedJedisPool ShardedJedisPool;  
	
	public static ShardedJedisPool getShardedJedisPool() throws Exception {
		if(ShardedJedisPool == null){
			before();
		}
		return ShardedJedisPool;
	}
	public ApplicationContext getAppContext() {
		return appContext;
	}

	@Before
	private static void before() throws Exception {  
		appContext = new ClassPathXmlApplicationContext("classpath:conf/spring/context.xml");  
		ShardedJedisPool = (ShardedJedisPool) appContext.getBean("shardedJedisPool");  
	}  
	
	@SuppressWarnings("static-access")
	public static void returnResource(RedisShardPool pool,ShardedJedis jedis) {
		if(jedis !=null){
			pool.ShardedJedisPool.returnResource(jedis);
		}
	}
	@SuppressWarnings("static-access")
	public static void returnBrokenResource(RedisShardPool pool,ShardedJedis jedis) {
			pool.ShardedJedisPool.returnBrokenResource(jedis);
	}
}
