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
import com.bert.domain.UserKey;

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
    
    
    /**
     * 查询全部
     */
    @Test
    public void testSelectAllByCriteria() {
        OrCriteria orCriteria = new OrCriteria();
        List<User> list = commonDaoMapper.selectByCriteria(User.class, orCriteria);
        for (User u : list) {
            System.out.println(u);
        }
    }
    
    
    /**
     * 条件查询
     */
    @Test
    public void testSelectByCriteria() {
        OrCriteria orCriteria = new OrCriteria();
        Criteria criteria = new Criteria();
        //condition = eq (=)
        criteria.add(Restrictions.eq("id", 2));
        //criteria.add(Restrictions.between("id", 1, 4));
        orCriteria.add(criteria);
        List<User> list = commonDaoMapper.selectByCriteria(User.class, orCriteria);
        for (User u : list) {
            System.out.println(u);
        }
    }
    
    /**
     * count统计
     */
    @Test
    public void testCountByCriteria() {
        OrCriteria orCriteria = new OrCriteria();
        Criteria criteria = new Criteria();
        //condition = gt(>)
        criteria.add(Restrictions.gt("id", 1));
        orCriteria.add(criteria);
        int count = commonDaoMapper.countByCriteria(User.class, orCriteria);
        System.out.println("count:"+count);
    }
    
    /**
     * 添加
     */
    @Test
    public void testInsert(){
    	User u  = new User();
    	u.setName("孙六");
    	u.setAge(14);
    	int count = commonDaoMapper.insert(u);
    	System.out.println("count:"+count);
    }
    
    /**
     * 删除数据ByPrimaryKey
     */
    @Test
    public void testDeleteByPrimaryKey() {
        UserKey key = new UserKey();
        key.setId(1);
        int count = commonDaoMapper.deleteByPrimaryKey(User.class, key);
        System.out.println("count:"+count);
    }
    
    /**
     * 删除数据ByCriteria
     */
    @Test
    public void testDeleteByCriteria() {
    	OrCriteria orCriteria = new OrCriteria();
        Criteria criteria = new Criteria();
        //condition = eq (=)
        criteria.add(Restrictions.eq("id", 2));
        //criteria.add(Restrictions.between("id", 1, 4));
        orCriteria.add(criteria);
        int count = commonDaoMapper.deleteByCriteria(User.class, orCriteria);
        System.out.println("count:"+count);
    }
    
    
    /**
     * 编辑ByPrimaryKey
     */
    @Test
    public void testUpdateByPrimaryKey(){
    	UserKey key = new UserKey();
    	key.setId(1);
    	User user = new User();
    	user.setName("张三");
    	int count = commonDaoMapper.updateByPrimaryKey(user, key);
    	System.out.println("count:"+count);
    }
    
    
    
    /**
     * 编辑ByCriteria
     */
    @Test
    public void testUpdateByCriteria(){
    	OrCriteria orCriteria = new OrCriteria();
    	Criteria criteria = new Criteria();
    	criteria.add(Restrictions.eq("id", 1));
    	orCriteria.add(criteria);
    	User user = new User();
    	user.setName("张三三");
    	int count = commonDaoMapper.updateByCriteria(user, orCriteria);
    	System.out.println("count:"+count);
    }
}
