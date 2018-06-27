package com.bert.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bert.common.util.RandomUtil;



@Controller
@RequestMapping("/led")
public class LedShowContoller {
	private static final Logger logger = LoggerFactory.getLogger(LedShowContoller.class);
	
	
	@RequestMapping(value = "/weather.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getWeather(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String weather = RandomUtil.getRandomString(6);
		logger.info("weather:"+weather);
		map.put("code", weather);
		return map;
	}
	
	
	
	@RequestMapping(value = "/stock.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getStock(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String stock = RandomUtil.getRandomString(6);
		logger.info("stock:"+stock);
		map.put("code", stock);
		return map;
	}
	
	
}
