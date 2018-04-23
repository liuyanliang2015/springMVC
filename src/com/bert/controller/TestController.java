package com.bert.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bert.core.user.service.UserService;
import com.bert.domain.User;
import com.google.gson.Gson;

//警告: No mapping found for HTTP request with URI [/SpringMVC/test/test.do] in DispatcherServlet with name 'dispatcher'
// remark4 :要想能够请求到contoller中的方法，必须在spring-mvc.xml配置文件中，配置下面的内容
//<context:component-scan base-package="com.bert.controller" use-default-filters="false">
@Controller
@RequestMapping("/test")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	@Resource
	private UserService userService;
	
	Gson g = new Gson();
	
	/**
	 * 测试类/test/test.do
	 * @param request
	 * remark5:不配置@ResponseBody，可以访问接口，但浏览器返回404
	 * remark6：配置上@ResponseBody，可以访问接口，但是浏览器返回406
	 * The resource identified by this request is only capable of generating responses with characteristics 
	 * not acceptable according to the request "accept" headers.
	 */
	@RequestMapping(value="/test.do",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> test(HttpServletRequest request){
		logger.info("call /test/test.do!");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		return map;
	}
	
	/**
	 * 查询数据库
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/queryJdbc.do",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryUser(HttpServletRequest request){
		logger.info("call /test/queryUser.do!");
		Map<String,Object> map = new HashMap<String, Object>();
		String sql = "SELECT * FROM tb_user";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			System.out.println(g.toJson(row));
			System.out.println(row.get("NAME"));
			map.put(row.get("NAME").toString(), row.get("AGE"));
		}
		return map;
	}
	
	/**
	 * <!--remark21：测试service->dao->mybatis-->
	 * 测试mybatis
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryMybatis.do",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryMybatis(HttpServletRequest request){
		logger.info("call /test/queryMybatis.do!");
		Map<String,Object> map = new HashMap<String, Object>();
		User user  = new User();
		user.setId(1);
		user = userService.getUser(user);
		map.put("data", user);
		return map;
	}
	
	
	/**
	 * remark25: 不配置视图解析器viewResolver，可以访问/index.jsp
	 * <property name="prefix" value="/WEB-INF/page/" />
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/testView1.do",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView testView1(HttpServletRequest request){
		logger.info("call /test/testView1.do!");
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("name", "lyl");
		return new ModelAndView("/index.jsp", model);
	}
	
	
	/**
	 * remark26:配置了视图解析器viewResolver，可以访问WEB-INF下面的页面
	 * <property name="prefix" value="/WEB-INF/page/" />
	 * 并且<property name="suffix" value=".jsp" />
	 * 已经添加了后缀名，new ModelAndView("/main", model);不用再加后缀名
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/testView2.do",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView testView2(HttpServletRequest request){
		logger.info("call /test/testView2.do!");
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("name", "lyl");
		return new ModelAndView("/main", model);
	}


}
