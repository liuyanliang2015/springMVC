package com.bert.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.ScriptSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bert.common.util.HttpRequestUtil;
import com.bert.dwr.DWRScriptSessionListener;
import com.bert.dwr.MessagePusher;

/**
 * DWR测试contoller
 * @author GW00165699
 */
@Controller
@RequestMapping("/led")
public class DwrWebController {
	
	/**
	 * 跳转到对应的测试页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toLed")
	@ResponseBody
	public ModelAndView toLed(HttpServletRequest request,HttpServletResponse response){
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
	
	
	/**
	 * 模拟给session1，推送信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/sendSingle")
	@ResponseBody
	public void sendSingle(HttpServletRequest request,HttpServletResponse response){
		MessagePusher push = new MessagePusher();
		push.sendMessage("1", "我很高兴啊！");
	}
	
	
	
	/**
	 * 模拟广播
	 * @param request
	 * @param response
	 */
	@RequestMapping("/sendBroadcast")
	@ResponseBody
	public void sendBroadcast(HttpServletRequest request,HttpServletResponse response){
		MessagePusher push = new MessagePusher();
		push.sendMessage("0", "我很高兴啊！");
	}
	
	/**
	 * 获取DWR-session个数
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getSessions")
	@ResponseBody
	public void getSessions(HttpServletRequest request,HttpServletResponse response){
		
		
		Map<String, ScriptSession> maps = DWRScriptSessionListener.scriptSessionMap;
		Iterator iterator = maps.keySet().iterator(); 
		while(iterator.hasNext()){
			String key = (String)iterator.next();
			System.out.println("key:"+key+", value:"+maps.get(key));
		}
		
		//得到所有ScriptSession  
		Collection<ScriptSession> sessions = DWRScriptSessionListener.getScriptSessions();
		System.out.println("HttpSessionKey size="+sessions.size());
	}
	
}