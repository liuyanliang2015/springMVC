package com.bert.task;

import org.aspectj.lang.JoinPoint;

/**
 * <p>任务处理器</p>
 * @author dubl 2015年6月11日
 * @version 1.0
 */
public interface TaskProcessor {
	/**
	 * 任务处理器是否接受任务处理工作
	 */
	public boolean accept(String className,String methodName);

	/**
	 * <p>任务完成处理工作</p>
	 * @author litr 2015-6-29
	 * @param joinPoint
	 * @throws Exception
	 */
	public void doAfterTask(JoinPoint joinPoint)throws Exception;
	
	/**
	 * <p>任务返回结果处理工作</p>
	 * @author litr 2015-6-29
	 * @param joinPoint
	 * @param result
	 * @throws Exception
	 */
	public void doReturningTask(JoinPoint joinPoint,Object result)throws Exception;
	
}
