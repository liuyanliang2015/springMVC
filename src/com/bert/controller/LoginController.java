package com.bert.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bert.common.jwt.Jwt;
import com.bert.common.util.HttpRequestUtil;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> paramMap = HttpRequestUtil.getParameterMap(request);
		String userName = paramMap.get("userName");
		String password = paramMap.get("password");
		if ("admin".equals(userName) && "123".equals(password)) {
			// 生成token
			Map<String, Object> payload = new HashMap<String, Object>();
			Date date = new Date();
			payload.put("uid", "admin");// 用户ID
			payload.put("iat", date.getTime());// 生成时间
			payload.put("ext", date.getTime() + 1000 * 60 * 60);// 过期时间1小时
			String token = Jwt.createToken(payload);
			System.out.println(token);
			result.put("token", token);
			result.put("status", 0);
			result.put("msg", "ok");
		} else {
			result.put("status", 10002);
			result.put("msg", "账号或者密码错误");
		}
		return result;
	}

}
