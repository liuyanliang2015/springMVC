package com.bert.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//警告: No mapping found for HTTP request with URI [/SpringMVC/test/test.do] in DispatcherServlet with name 'dispatcher'
// remark4 :要想能够请求到contoller中的方法，必须在spring-mvc.xml配置文件中，配置下面的内容
//<context:component-scan base-package="com.bert.controller" use-default-filters="false">
@Controller
@RequestMapping("/test")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	/**
	 * 测试类/test/test.do
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/test.do",method=RequestMethod.GET)
	public Map<String,Object> test(HttpServletRequest request){
		logger.info("call /test/test.do!");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		return map;
	}

}
