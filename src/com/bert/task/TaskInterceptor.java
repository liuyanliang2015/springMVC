package com.bert.task;

import org.aspectj.lang.JoinPoint;

/**
 * <p>任务处理器</p>
 * @author dubl 2015年6月11日
 * @version 1.0
 */
public interface TaskInterceptor {
	
	/**
	 * <p>拦截方法执行完成后事件</p>
	 * @author litr 2015-6-29
	 * @param joinPoint
	 * @throws Throwable
	 */
	public void doAfter(JoinPoint joinPoint) throws Throwable;
	
	/**
	 * <p>拦截方法执行返回后事件</p>
	 * @author litr 2015-6-29
	 * @param joinPoint
	 * @param result
	 * @throws Throwable
	 */
	public void doReturning(JoinPoint joinPoint,Object result) throws Throwable;
	
	/**
	 * <p>拦截方法抛出异常事件</p>
	 * @author litr 2015-6-29
	 * @param ex
	 */
	public void doThrowing(Throwable ex);
}
