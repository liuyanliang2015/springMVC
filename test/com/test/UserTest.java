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
import com.bert.core.user.service.UserService;
import com.bert.domain.User;

/**
 * jdbc&serice测试
 * @author Administrator	
 */
public class UserTest extends BaseSpringTestCase {
	
	//remark11 使用注解@Autowired装配JdbcTemplate
	//@Autowired和@Resource都可以，又什么区别呢？
	/**
	 * @Resource默认按照名称方式进行bean匹配，@Autowired默认按照类型方式进行bean匹配
	 * @Resource是J2EE的注解，而@Autowired是spring的注解
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserService userservice;
	
	
	/**
	 * jdbcTemplate-query测试
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@org.junit.Test
	public void testQuery() throws Exception {
		try {
			//remark12   JdbcTemplate用法
			List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from tb_user");
			for (Map row : rows) {
				System.out.println(row.get("NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * jdbcTemplate-update测试
	 * 如果@Rollback(fasle)
	 * 信息: Committed transaction for test: [DefaultTestContext@528b376 testClass = UserRoleDealTest, testInstance = com.test.UserRoleDealTest@331e0a9f, testMethod = testUpdate@UserRoleDealTest, testException = [null], mergedContextConfiguration = [MergedContextConfiguration@73c2a0d9 testClass = UserRoleDealTest, locations = '{classpath:spring_test.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]
	 * 如果@Rollback(true)
	 * 信息: Rolled back transaction for test: [DefaultTestContext@331e0a9f testClass = UserRoleDealTest, testInstance = com.test.UserRoleDealTest@794b66f0, testMethod = testUpdate@UserRoleDealTest, testException = [null], mergedContextConfiguration = [MergedContextConfiguration@6ece41ee testClass = UserRoleDealTest, locations = '{classpath:spring_test.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextCustomizers = set[[empty]], contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]]
	 * @throws Exception
	 */
	@org.junit.Test
	//@Transactional
	//@Rollback(false)
	public void testUpdate() throws Exception {
		try {
			jdbcTemplate.update("UPDATE tb_user SET NAME='张三' WHERE ID = 1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * service测试
	 * @throws Exception
	 */
	@org.junit.Test
	public void testService() throws Exception {
		try {
			User u = new User();
			u.setId(1);
			u = userservice.getUser(u);
			System.out.println(u.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
