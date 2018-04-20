/**
 * 文件名:OilPriceDealTest.java 
 * 工程名：osmp
 * 包名:osmp
 * 作者:Administrator
 * 创建时间:2017年10月26日
 * Copyright (C) 2017 绿蜘蛛科技有限公司
 */
package com.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.base.BaseSpringTestCase;

/**
 * @author Administrator	
 */
public class UserRoleDealTest extends BaseSpringTestCase {
	
	//remark9  使用注解@Autowired装配JdbcTemplate
	//@Autowired和@Resource都可以，又什么区别呢？
	/**
	 * @Resource默认按照名称方式进行bean匹配，@Autowired默认按照类型方式进行bean匹配
	 * @Resource是J2EE的注解，而@Autowired是spring的注解
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	@org.junit.Test
	public void testData() throws Exception {
		try {
			//remark10   JdbcTemplate用法
			List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from tb_user");
			for (Map row : rows) {
				System.out.println(row.get("NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
