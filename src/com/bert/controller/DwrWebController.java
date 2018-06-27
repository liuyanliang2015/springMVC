package com.bert.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bert.common.util.HttpRequestUtil;
import com.bert.dwr.MessagePusher;

@Controller
@RequestMapping("/led")
public class DwrWebController {
	//private static Logger log = LoggerFactory.getLogger(DwrWebController.class);
	
	@RequestMapping("/toLed")
	@ResponseBody
	public ModelAndView dwrReceiveMsg(HttpServletRequest request,HttpServletResponse response){
		Map<String, String> paramMap = HttpRequestUtil.getParameterMap(request);
		String userId = paramMap.get("userId");
		
		String jspPath = null;
		if("1".equals(userId)){
			jspPath = "/led_weather";
					
		}else if("2".equals(userId)){
			jspPath = "/led_stock";
		}else{
			jspPath = "/led_main";
		}
		ModelAndView mv = new ModelAndView(jspPath);
		return mv;
	}
	
	
	
	@RequestMapping("/send")
	@ResponseBody
	public void send(HttpServletRequest request,HttpServletResponse response){
		MessagePusher push = new MessagePusher();
		push.sendMessage("1", "我很高兴啊！");
		
	}
	
}