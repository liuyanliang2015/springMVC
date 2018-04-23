package com.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import redis.clients.jedis.ShardedJedis;

import com.base.BaseSpringTestCase;
import com.bert.redis.RedisService;
import com.bert.redis.RedisShardPool;
/**
 * remark31 : redis是一个key-value存储系统。
 * 和Memcached类似，它支持存储的value类型相对更多，
 * 包括string(字符串)、list(链表)、set(集合)、zset(sorted set --有序集合)和hash（哈希类型）
 * 这些数据类型都支持push/pop、add/remove及取交集并集和差集及更丰富的操作，而且这些操作都是原子性的。
 * redis支持各种不同方式的排序。与memcached一样，为了保证效率，数据都是缓存在内存中。
 * 区别的是redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。
 */
public class RedisTest extends BaseSpringTestCase {
	protected RedisShardPool pool;
	protected ShardedJedis jedis = null;
	String key = "test_key";
	int expireTimeSecond = 5;
	
	@Resource(name = "redisService")
	private RedisService redisService ;
	
	/**
	 * 存储和获取字符串
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void testRedis1() throws Exception {
		try {
			jedis = pool.getShardedJedisPool().getResource();
			jedis.set(key, "hello");
			String value = jedis.get(key);
			System.out.println("value:"+value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 设置失效时间，单位秒
	 * expire(String key,int seconds)
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void testRedis2() throws Exception {
		try {
			jedis = pool.getShardedJedisPool().getResource();
			jedis.set(key, "java");
			String value = jedis.get(key);
			jedis.expire(key, expireTimeSecond);
			System.out.println("value:"+value+",expire:"+expireTimeSecond);
			Thread.sleep(6000);
			value = jedis.get(key);
			System.out.println("value:"+value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除数据
	 * del(String key)
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void testRedis3() throws Exception {
		try {
			jedis = pool.getShardedJedisPool().getResource();
			jedis.set(key, "java");
			String value = jedis.get(key);
			System.out.println("value:"+value);
			long result = jedis.del(key);
			System.out.println("result:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 存储集合数据
	 * @throws Exception
	 */
	@org.junit.Test
	public void testRedis4() throws Exception {
		try {
			List<String> list = new ArrayList<String>();
			list.add("a");
			list.add("b");
			list.add("c");
			redisService.setList(key, list);
			List<String> list2 = redisService.getList(key, String.class);
			for(String l : list2){
				System.out.println(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	



}
