package com.bert.redis;

import redis.clients.jedis.JedisShardInfo;

public class NewJedisShardInfo extends JedisShardInfo {

	public NewJedisShardInfo(String host, int port, String password) {
		this(host, port, 2000, password);
	}
	 public NewJedisShardInfo(String host, int port, int timeout, String password) {
		super(host, port, timeout, 1);
		super.setPassword(password);
	}
}
