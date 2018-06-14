package com.test;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bert.common.batis.Criteria;
import com.bert.common.batis.OrCriteria;
import com.bert.common.batis.criterion.Restrictions;
import com.bert.common.batis.dao.mapper.CommonDaoMapper;
import com.bert.common.batis.plugin.Transfer;
import com.bert.domain.User;

public class CommonMybatisTest {

    private static ApplicationContext context;
    private static SqlSessionTemplate sessionTemplate;
    private static CommonDaoMapper commonDaoMapper;

    
    @Before
    public void before(){
    	try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        sessionTemplate = context.getBean(SqlSessionTemplate.class);
        sessionTemplate.getConfiguration().addInterceptor(new Transfer());
        commonDaoMapper = context.getBean(CommonDaoMapper.class);
    }
    
    
    @Test
    public void testSelectByCriteria() {
        System.out.println("--------------start to test selectByCriteria--------------------");
        OrCriteria orCriteria = new OrCriteria();
        Criteria criteria = new Criteria();
        criteria.add(Restrictions.eq("id", 1));
        orCriteria.add(criteria);
        List<User> list = commonDaoMapper.selectByCriteria(User.class, orCriteria);
        for (User u : list) {
            System.out.println(u);
        }
        System.out.println("---------------end to test selectByCriteria------------------------");
    }

}
