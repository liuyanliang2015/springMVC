package com.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import redis.clients.jedis.ShardedJedis;

import com.base.BaseSpringTestCase;
import com.bert.redis.RedisService;
import com.bert.redis.RedisShardPool;

/**
 * redis测试 remark31 : redis是一个key-value存储系统。 和Memcached类似，它支持存储的value类型相对更多，
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
	private RedisService redisService;

	
	/**
	 * 存储List数据类型(新疆白名单)
	 */
	@org.junit.Test
	public void testRedis4() throws Exception {
		try {
			List<String> list = new ArrayList<String>();
			list.add("49.112.0.0-49.119.255.255");
			list.add("222.80.0.0-222.81.255.255");
			list.add("124.117.0.0-124.117.255.255");
			list.add("222.83.0.0-222.83.127.255");
			list.add("202.100.160.0-202.100.167.255");
			list.add("218.84.0.0-218.87.255.255");
			list.add("124.118.0.0-124.119.255.255");
			list.add("222.82.0.0-222.82.255.255");
			list.add("221.7.0.0-221.7.31.255");
			list.add("103.3.136.0-103.3.139.255");
			list.add("110.152.0.0-110.155.255.255");
			list.add("110.156.0.0-110.157.255.255");
			list.add("60.13.128.0-60.13.255.255");
			list.add("202.100.176.0-202.100.191.255");
			list.add("103.22.116.0-103.22.119.255");
			list.add("120.68.0.0-120.71.255.255");
			list.add("124.88.0.0-124.88.255.255");
			list.add("202.107.128.0-202.107.255.255");
			list.add("202.100.168.0-202.100.175.255");
			redisService.setList("WHITE_LIST", list);
			List<String> list2 = redisService.getList("WHITE_LIST",
					String.class);
			for (String l : list2) {
				System.out.println(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 获取list数据
	 */
	@org.junit.Test
	public void testGetList() throws Exception {
		try {
			System.out.println("---------------新疆IP白名单--------------------");
			List<String> list1 = redisService.getList("WHITE_LIST",String.class);
			for (String l : list1) {
				System.out.println(l);
			}
			
			System.out.println("-------------IP黑名单----------------------");
			List<String> list2 = redisService.getList("BLACK_LIST",String.class);
			if (list2 != null) {
				for (String l : list2) {
					System.out.println(l);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 存储String数据
	 */
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void testRedis1() throws Exception {
		try {
			jedis = pool.getShardedJedisPool().getResource();
			jedis.set(key, "hello");
			String value = jedis.get(key);
			System.out.println("value:" + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除redis数据
	 */
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void testDelRedis() throws Exception {
		try {
			jedis = pool.getShardedJedisPool().getResource();
			long r = jedis.del("WHITE_LIST");
			System.out.println(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 设置失效时间，单位秒 expire(String key,int seconds)
	 */
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void testRedis2() throws Exception {
		try {
			jedis = pool.getShardedJedisPool().getResource();
			jedis.set(key, "java");
			String value = jedis.get(key);
			jedis.expire(key, expireTimeSecond);
			System.out.println("value:" + value + ",expire:" + expireTimeSecond);
			Thread.sleep(6000);
			value = jedis.get(key);
			System.out.println("value:" + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

	

}
