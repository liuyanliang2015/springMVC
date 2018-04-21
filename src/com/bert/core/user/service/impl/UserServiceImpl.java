package com.bert.core.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bert.core.user.dao.UserDao;
import com.bert.core.user.service.UserService;
import com.bert.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	
	@Override
	public User getUser(User user) {
		return userDao.getUser(user);
	}

}
