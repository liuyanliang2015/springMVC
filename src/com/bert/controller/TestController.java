package com.bert.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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

import com.bert.common.batis.Condition;
import com.bert.common.batis.Criteria;
import com.bert.common.batis.criterion.Restrictions;
import com.bert.common.batis.dao.mapper.CommonDaoMapper;
import com.bert.common.util.HttpRequestUtil;
import com.bert.common.util.SignUtil;
import com.bert.core.user.service.UserService;
import com.bert.domain.User;
import com.google.gson.Gson;

//警告: No mapping found for HTTP request with URI [/SpringMVC/test/test.do] in DispatcherServlet with name 'dispatcher'
// remark4 :要想能够请求到contoller中的方法，必须在spring-mvc.xml配置文件中，配置下面的内容
//<context:component-scan base-package="com.bert.controller" use-default-filters="false">
@Controller
@RequestMapping("/test")
// remark:Contoller默认scope = singleton单例模式，效率高，但是有线程安全问题
// remark:scope =prototype,每次请求生成一个新的Contoller
// @Scope("prototype")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private CommonDaoMapper commonDaoMapper;

	@Resource
	private UserService userService;

	Gson g = new Gson();

	/**
	 * 测试类/test/test.do
	 * 
	 * @param request
	 *            remark5:不配置@ResponseBody，可以访问接口，但浏览器返回404
	 *            remark6：配置上@ResponseBody，可以访问接口，但是浏览器返回406 The resource
	 *            identified by this request is only capable of generating
	 *            responses with characteristics not acceptable according to the
	 *            request "accept" headers.
	 */
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> test(HttpServletRequest request) {
		logger.info("call /test/test.do!");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		return map;
	}

	/**
	 * 查询数据库
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryJdbc.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryJdbc(HttpServletRequest request) {
		logger.info("call /test/queryUser.do!");
		Map<String, Object> map = new HashMap<String, Object>();
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
	 * <!--remark21：测试service->dao->mybatis--> 测试mybatis
	 * 
	 * @param request
	 *            http://localhost:8080/SpringMVC/test/queryMybatis.do?uid=1&
	 *            nonce_str=abcd&timestamp=1234567890&sign=
	 *            fc71545ce59dbddee44e4447c5e981e6bb8ddc03420dd3081e0e82c5ec03c4e1
	 * @return
	 */
	@RequestMapping(value = "/queryMybatis.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryMybatis(HttpServletRequest request) {
		logger.info("call /test/queryMybatis.do!");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> paramMap = HttpRequestUtil.getParameterMap(request);
		String sign = paramMap.get("sign");

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("uid", paramMap.get("uid"));
		parameters.put("nonce_str", paramMap.get("nonce_str"));
		parameters.put("timestamp", paramMap.get("timestamp"));

		boolean ifSign = SignUtil.verify2(sign, "UTF-8", parameters);
		if (!ifSign) {
			result.put("status", 10001);
			result.put("msg", "sign错误");
			return result;
		}
		User user = new User();
		user.setId(1);
		user = userService.getUser(user);
		result.put("data", user);
		result.put("status", 0);
		result.put("msg", "ok");
		return result;
	}

	@RequestMapping(value = "/queryCommonMybatis.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryCommonMybatis(HttpServletRequest request) {
		logger.info("call /test/queryCommonMybatis.do!");
		Map<String, Object> result = new HashMap<String, Object>();
		Condition condition = new Condition();
		Criteria criteria = new Criteria();
		// condition = eq (=)
		criteria.add(Restrictions.eq("id", 2));
		// criteria.add(Restrictions.between("id", 1, 4));
		condition.add(criteria);
		List<User> list = commonDaoMapper.selectByCriteria(User.class, condition);
		result.put("data", list);
		result.put("status", 0);
		result.put("msg", "ok");
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getData.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		logger.info("call /test/getData.do!");
		Map<String, Object> result = new HashMap<String, Object>();
		HashMap<String, String> data = (HashMap<String, String>) request.getAttribute("data");
		// payload中的数据可以用来做查询，比如我们在登陆成功时将用户ID存到了payload中，我们可以将它取出来，去数据库查询这个用户的所有信息；
		// 而不是用request.getParameter("uid")方法来获取前端传给我们的uid，因为前端的参数时可篡改的不完全可信的，而我们从payload中取出来的数据是从token中
		// 解密取出来的，在秘钥没有被破解的情况下，它是绝对可信的；这样可以避免别人用这个接口查询非自己用户ID的相关信息
		result.put("data", data);
		result.put("status", 0);
		result.put("msg", "ok");
		return result;
	}

	/**
	 * remark25: 不配置视图解析器viewResolver，可以访问/index.jsp
	 * <property name="prefix" value="/WEB-INF/page/" />
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/testView1.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView testView1(HttpServletRequest request) {
		logger.info("call /test/testView1.do!");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "lyl");
		return new ModelAndView("/index.jsp", model);
	}

	/**
	 * remark26:配置了视图解析器viewResolver，可以访问WEB-INF下面的页面
	 * <property name="prefix" value="/WEB-INF/page/" /> 并且
	 * <property name="suffix" value=".jsp" /> 已经添加了后缀名，new
	 * ModelAndView("/main", model);不用再加后缀名
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/testView2.do", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView testView2(HttpServletRequest request) {
		logger.info("call /test/testView2.do!");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "lyl");
		return new ModelAndView("/main", model);
	}

	/**
	 * 测试AOP切面编程
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/testAop.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> testAop(HttpServletRequest request) {
		logger.info("call /test/testAop.do!");
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setId(1);
		user = userService.getUser(user);
		map.put("data", user);
		map.put("pushStatus", 1);
		return map;
	}

	private static int a = 0;
	private int index = 0;

	/**
	 * 测试controller的线程安全问题
	 * 多次调用，发现线程id、this.hashCode()、this都是一样的，说明Contoller对象是单例的
	 * jmeter多线程调用，会发现变量index为所有请求共享，会出现线程安全问题
	 * 解决方法1：为Contoller加上@Scope("prototype")注解，Contoller非单例，会发现static修饰的a共享，
	 * 但是index已经是每个线程独有
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/testThreadSafe.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> testThreadSafe(HttpServletRequest request) {
		// logger.info("thread:"+
		// Thread.currentThread()+",hashCode:"+this.hashCode()+","+this);
		logger.info(a++ + " | " + index++);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", 0);
		return map;
	}

}
