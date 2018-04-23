package com.bert.task.support;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bert.core.user.service.UserService;
import com.bert.domain.User;
import com.bert.task.AbstractTaskProcessor;

public class TestAOPTaskProcessor extends AbstractTaskProcessor {

	private static final Logger logger = LoggerFactory.getLogger(TestAOPTaskProcessor.class);

	private UserService userService;

	@Override
	public void doAfterTask(JoinPoint joinPoint) throws Exception {

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void doReturningTask(JoinPoint joinPoint, Object result)
			throws Exception {
		// 转换数据
		Map<String, Object> map = null;
		if (result instanceof Map) {
			map = (Map) result;
		}
		if (!map.isEmpty()) {
			Integer pushStatus = Integer.parseInt(map.get("pushStatus").toString());
			logger.info("pushStatus->{}", pushStatus);
			if (pushStatus == 1) {
				// 执行相应的业务逻辑
				User user = new User();
				user.setId(1);
				user = userService.getUser(user);
				logger.info("userName->{}", user.getName());
			}
		}

	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
