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

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.base.BaseSpringTestCase;

/**
 * @author Administrator	
 */
public class UserRoleDealTest extends BaseSpringTestCase {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("rawtypes")
	@org.junit.Test
	public void testData() throws Exception {
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from tb_user");
			for (Map row : rows) {
				System.out.println(row.get("NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
