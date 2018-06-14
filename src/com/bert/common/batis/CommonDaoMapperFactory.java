package com.bert.common.batis;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bert.common.batis.dao.mapper.CommonDaoMapper;
import com.bert.common.batis.plugin.Transfer;

public class CommonDaoMapperFactory {
	private CommonDaoMapperFactory(){
		
	}
	private static ApplicationContext context;
    private static SqlSessionTemplate sessionTemplate;
    private static CommonDaoMapper commonDaoMapper;
    
    public static CommonDaoMapper getCommonDaoMapper(){
    		if(commonDaoMapper == null){
    			synchronized (CommonDaoMapperFactory.class) {
	        		try {
	                    context = new ClassPathXmlApplicationContext("applicationContext.xml");
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                sessionTemplate = context.getBean(SqlSessionTemplate.class);
	                sessionTemplate.getConfiguration().addInterceptor(new Transfer());
	                commonDaoMapper = context.getBean(CommonDaoMapper.class);
        	}
		}
    	System.out.println(commonDaoMapper.hashCode());
        return commonDaoMapper;
    }
}
