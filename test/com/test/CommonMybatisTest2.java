package com.test;


import java.util.List;

import org.junit.Test;

import com.base.BaseSpringTestCase;
import com.bert.common.batis.CommonDaoMapperFactory;
import com.bert.common.batis.Criteria;
import com.bert.common.batis.Condition;
import com.bert.common.batis.criterion.Restrictions;
import com.bert.domain.User;
import com.bert.domain.UserKey;

public class CommonMybatisTest2 extends BaseSpringTestCase{
	

    /**
     * 查询全部
     */
    @Test
    public void testSelectAllByCriteria() {
        Condition condition = new Condition();
        List<User> list = CommonDaoMapperFactory.getCommonDaoMapper().selectByCriteria(User.class, condition);
        for (User u : list) {
            System.out.println(u);
        }
    }
    
    
    /**
     * 条件查询
     */
    @Test
    public void testSelectByCriteria() {
        Condition condition = new Condition();
        Criteria criteria = new Criteria();
        //condition = eq (=)
        criteria.add(Restrictions.eq("id", 2));
        //criteria.add(Restrictions.between("id", 1, 4));
        condition.add(criteria);
        List<User> list = CommonDaoMapperFactory.getCommonDaoMapper().selectByCriteria(User.class, condition);
        for (User u : list) {
            System.out.println(u);
        }
    }
    
    /**
     * count统计
     */
    @Test
    public void testCountByCriteria() {
        Condition condition = new Condition();
        Criteria criteria = new Criteria();
        //condition = gt(>)
        criteria.add(Restrictions.gt("id", 1));
        condition.add(criteria);
        int count = CommonDaoMapperFactory.getCommonDaoMapper().countByCriteria(User.class, condition);
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
    	int count = CommonDaoMapperFactory.getCommonDaoMapper().insert(u);
    	System.out.println("count:"+count);
    }
    
    /**
     * 删除数据ByPrimaryKey
     */
    @Test
    public void testDeleteByPrimaryKey() {
        UserKey key = new UserKey();
        key.setId(1);
        int count = CommonDaoMapperFactory.getCommonDaoMapper().deleteByPrimaryKey(User.class, key);
        System.out.println("count:"+count);
    }
    
    /**
     * 删除数据ByCriteria
     */
    @Test
    public void testDeleteByCriteria() {
    	Condition condition = new Condition();
        Criteria criteria = new Criteria();
        //condition = eq (=)
        criteria.add(Restrictions.eq("id", 2));
        //criteria.add(Restrictions.between("id", 1, 4));
        condition.add(criteria);
        int count = CommonDaoMapperFactory.getCommonDaoMapper().deleteByCriteria(User.class, condition);
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
    	int count = CommonDaoMapperFactory.getCommonDaoMapper().updateByPrimaryKey(user, key);
    	System.out.println("count:"+count);
    }
    
    
    
    /**
     * 编辑ByCriteria
     */
    @Test
    public void testUpdateByCriteria(){
    	Condition condition = new Condition();
    	Criteria criteria = new Criteria();
    	criteria.add(Restrictions.eq("id", 1));
    	condition.add(criteria);
    	User user = new User();
    	user.setName("张三三");
    	int count = CommonDaoMapperFactory.getCommonDaoMapper().updateByCriteria(user, condition);
    	System.out.println("count:"+count);
    }
}
