package com.thinkis.modules.app.service;


import com.thinkis.modules.app.form.LoginForm;
import com.thinkis.modules.sys.entity.User;

/**
 * 用户
 * 
 * @author liuzhiping
 * @date 2018-4-27 22:20:30
 */
public interface UserService {

	User queryByAccount(String account);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	String login(LoginForm form);
}
