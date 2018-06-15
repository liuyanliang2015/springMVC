package com.bert.core.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bert.core.user.dao.UserDaoMapper;
import com.bert.core.user.service.UserService;
import com.bert.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDaoMapper userDaoMapper;
	
	
	@Override
	public User getUser(User user) {
		return userDaoMapper.selectByPrimaryKey(User.class, user).get(0);
		//return userDao.getUser(user);
	}

}
