package com.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author Administrator	
 */
@SuppressWarnings("deprecation")
//remark18:事务支持(全局配置，这样写不能加载具体的方法上)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)  
@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring_test.xml"
})
public class BaseSpringTestCase {

}
