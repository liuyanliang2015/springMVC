package com.test;

import org.junit.Test;

import com.bert.common.util.HttpClientUtil;

public class LedShowTest {
	
	/**
	 * 测试天气预报
	 */
	@Test
	public void testWeather(){
		String reqURL = "http://v.juhe.cn/weather/index?format=2&cityname=北京&key=34b866ec6167e6966ce5058fd97672d0";
		String response = HttpClientUtil.sendGetRequest(reqURL, "UTF-8");
		System.out.println(response);
	}
	
	
	/**
	 * 测试股票数据
	 * 股票编号，上海股市以sh开头，深圳股市以sz开头如：sh601009（type为0或者1时gid不是必须）
	 */
	@Test
	public void testStock(){
		String reqURL = "http://web.juhe.cn:8080/finance/stock/hs?gid=sh601009&key=fb5b8c54d4c07f3e69510096d6a32104";
		String response = HttpClientUtil.sendGetRequest(reqURL, "UTF-8");
		System.out.println(response);
	}

}
