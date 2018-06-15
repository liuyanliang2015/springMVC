package com.bert.core.user.dao;

import com.bert.common.batis.dao.mapper.CommonDaoMapper;
import com.bert.domain.User;

public interface UserDaoMapper extends CommonDaoMapper{
	public User getUser(User user);
}
