package com.bert.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * remark32: spring上下文工具类
 * @author Administrator
 *
 */
public class ApplicationContextUtil {
	private static ApplicationContext context;
	static {
		context = new ClassPathXmlApplicationContext("/conf/spring/context.xml");
	}
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
}
