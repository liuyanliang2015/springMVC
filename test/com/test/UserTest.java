/**
 * 文件名:OilPriceDealTest.java 
 * 工程名：osmp
 * 包名:osmp
 * 作者:Administrator
 * 创建时间:2017年10月26日
 * Copyright (C) 2017 绿蜘蛛科技有限公司
 */
package com.test;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.base.BaseSpringTestCase;
import com.bert.core.user.service.UserService;
import com.bert.domain.User;
import com.bert.util.ApplicationContextUtil;

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
	
	
	//remark38: 使用Autowired注解装配spring的bean
	@Autowired
	User user;
	
	
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
				System.out.println(row.get("name"));
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
	
	
	
	/**
	 * 反射机制创建对象
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@org.junit.Test
	public void testCreateBean1() throws Exception {
		Class c = Class.forName("com.bert.domain.User");
		//无参构造器
		User u1 = (User)c.newInstance();
		System.out.println(u1.toString());
		System.out.println("--------------------------");
		//有参构造器
		Constructor<?> con = c.getConstructor(String.class,Integer.class);
		User u2 = (User)con.newInstance("lyl",30);
		System.out.println(u2.toString());
		
	}
	
	
	
	/*  remark33: spring创建对象，用了反射机制，默认scope="singleton"单例模式
	            伪代码的形式来模拟实现:
		//解析<bean .../>元素的id属性得到该字符串值为“courseDao”  
		String idStr = "courseDao";  
		//解析<bean .../>元素的class属性得到该字符串值为“com.qcjy.learning.Dao.impl.CourseDaoImpl”  
		String classStr = "com.qcjy.learning.Dao.impl.CourseDaoImpl";  
		//利用反射知识，通过classStr获取Class类对象  
		Class<?> cls = Class.forName(classStr);  
		//实例化对象  
		Object obj = cls.newInstance();  
		//container表示Spring容器  
		container.put(idStr, obj);
	 */
	@org.junit.Test
	public void testCreateBean2() throws Exception {
		ApplicationContext con = ApplicationContextUtil.getApplicationContext();
		User user1 = (User)con.getBean("user");
		System.out.println(user1.hashCode());
		System.out.println("---------------------------");
		User user2 = (User)con.getBean("user");
		System.out.println(user2.hashCode());
		System.out.println("---------------------------");
		User user3 = (User)con.getBean("user1");
		System.out.println(user3.toString());
		
		System.out.println("---------------------------");
		User user4= (User)con.getBean("user2");
		System.out.println(user4.toString());
		
		System.out.println("---------------------------");
		//remark38: 使用Autowired注解装配spring的bean
		System.out.println(user.toString());
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
