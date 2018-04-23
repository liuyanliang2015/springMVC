package com.test;

import redis.clients.jedis.ShardedJedis;

import com.base.BaseSpringTestCase;
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
	
	@SuppressWarnings("static-access")
	@org.junit.Test
	public void testRedis() throws Exception {
		try {
			jedis = pool.getShardedJedisPool().getResource();
			jedis.set("test_key1", "hello");
			String value = jedis.get("test_key1");
			System.out.println("value:"+value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
