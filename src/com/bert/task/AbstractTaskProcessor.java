package com.bert.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;

/**
 * <p>任务处理抽象类</p>
 * @author dubl 2015年6月11日
 * @version 1.0
 */
public abstract class AbstractTaskProcessor implements TaskProcessor {
	/**
	 * 拦截信息
	 */
	private List<String> interceptInfos;
	
	/**
	 * 信息
	 */
	private Map<String,String> infoMap = new HashMap<String,String>();

	@Override
	public boolean accept(String className,String methodName) {
		if(interceptInfos != null && interceptInfos.size() > 0){
			return infoMap.get(className + ":" + methodName) != null;
		}
		return false;
	}

	public abstract void doAfterTask(JoinPoint joinPoint) throws Exception;
	
	public abstract void doReturningTask(JoinPoint joinPoint,Object result)throws Exception;

	public List<String> getInterceptInfos() {
		return interceptInfos;
	}

	public void setInterceptInfos(List<String> interceptInfos) {
		if(interceptInfos!=null && interceptInfos.size()>0){
			for(String info:interceptInfos){
				infoMap.put(info, info);
			}
		}
		this.interceptInfos = interceptInfos;
	}
}
