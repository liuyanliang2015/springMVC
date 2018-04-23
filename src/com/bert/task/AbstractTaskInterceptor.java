package com.bert.task;

import java.util.List;

import org.aspectj.lang.JoinPoint;

/**
 * <p>任务拦截器</p>
 * @author dubl 2015年6月11日
 * @version 1.0
 */
public abstract class AbstractTaskInterceptor implements TaskInterceptor {
	
	/**
	 * 过滤器链
	 */
	private List<TaskProcessor> processors;

	/**
	 * <p>拦截方法执行完成后事件</p>
	 * @author dubl 2015年6月11日
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Override
	public void doAfter(JoinPoint joinPoint) throws Throwable {
       String className = joinPoint.getTarget().getClass().getName();
       String methodName = joinPoint.getSignature().getName();
       if(processors!=null && processors.size()>0){
    	   for(TaskProcessor processor:processors){
    		   if(processor.accept(className, methodName)){
    			   processor.doAfterTask(joinPoint);
    		   }
    	   }
       }
    }
	
	@Override
	public void doReturning(JoinPoint joinPoint,Object result) throws Throwable {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		if(processors!=null && processors.size()>0){
			for(TaskProcessor processor:processors){
				if(processor.accept(className, methodName)){
					processor.doReturningTask(joinPoint, result);
				}
			}
		}
	}
	
	/**
	 * <p>异常处理</p>
	 * @author dubl 2015年6月11日
	 * @param ex
	 */
	@Override
	public void doThrowing(Throwable ex){
		
	}

	public List<TaskProcessor> getProcessors() {
		return processors;
	}

	public void setProcessors(List<TaskProcessor> processors) {
		this.processors = processors;
	}
}
