package com.test;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.base.BaseSpringTestCase;
import com.bert.common.batis.Condition;
import com.bert.common.batis.Criteria;
import com.bert.common.batis.criterion.Restrictions;
import com.bert.common.batis.dao.mapper.CommonDaoMapper;
import com.bert.domain.User;
import com.bert.domain.UserKey;

public class CommonMybatisNomalTest extends BaseSpringTestCase{
	
	@Autowired
	private CommonDaoMapper commonDaoMapper;
	
    /**
     * 查询全部
     */
    @Test
    public void testSelectAllByCriteria() {
        Condition condition = new Condition();
        List<User> list = commonDaoMapper.selectByCriteria(User.class, condition);
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
        List<User> list = commonDaoMapper.selectByCriteria(User.class, condition);
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
        int count = commonDaoMapper.countByCriteria(User.class, condition);
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
    	Condition condition = new Condition();
        Criteria criteria = new Criteria();
        //condition = eq (=)
        criteria.add(Restrictions.eq("id", 2));
        //criteria.add(Restrictions.between("id", 1, 4));
        condition.add(criteria);
        int count = commonDaoMapper.deleteByCriteria(User.class, condition);
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
    	Condition condition = new Condition();
    	Criteria criteria = new Criteria();
    	criteria.add(Restrictions.eq("id", 1));
    	condition.add(criteria);
    	User user = new User();
    	user.setName("张三三");
    	int count = commonDaoMapper.updateByCriteria(user, condition);
    	System.out.println("count:"+count);
    }
}
