package com.thinkis.modules.app.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkis.modules.app.exception.AppException;
import com.thinkis.modules.app.form.LoginForm;
import com.thinkis.modules.app.service.UserService;
import com.thinkis.modules.app.util.Assert;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.service.SystemService;
import com.thinkis.modules.sys.utils.UserUtils;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Override
	public User queryByAccount(String account) {
		return UserUtils.getByLoginName(account);
	}

	@Override
	public String login(LoginForm form) {
		User user = queryByAccount(form.getAccount());
		Assert.isNull(user, "账号或密码错误");
		
		//验证密码
		if(!SystemService.validatePassword(form.getPassword(),user.getPassword())){
			throw new AppException("账号或密码错误");
		}

		return user.getId();
	}
}
