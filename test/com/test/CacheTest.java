package com.test;

import javax.annotation.Resource;

import com.base.BaseSpringTestCase;
import com.bert.core.user.service.UserService;
import com.bert.domain.User;
import com.bert.factory.EhCacheFactory;
import com.google.gson.Gson;

/**
 * ehcache测试
 */
public class CacheTest extends BaseSpringTestCase {
	@Resource(name = "userService")
	private UserService userService ;
	Gson g = new Gson();
	
	@org.junit.Test
	public void testCache() throws Exception {
		try {
			User user  = new User();
			user.setId(1);
			user = userService.getUser(user);
			EhCacheFactory.cacheData("userDataCache", "userList", user);
			Object userCashData = EhCacheFactory.getCacheData("userDataCache", "userList");
			System.out.println(g.toJson(userCashData));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
