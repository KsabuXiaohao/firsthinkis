/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.sys.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.thinkis.common.service.ServiceException;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.modules.sys.entity.Office;
import com.thinkis.modules.sys.entity.Role;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.security.UsernamePasswordToken;
import com.thinkis.modules.sys.service.SystemService;

import cn.hutool.http.HttpUtil;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
@RequestMapping(value = "${frontPath}")
public class RegisterController extends BaseController{
	
	@Autowired
	private SystemService systemService;
	
	@Value("${register.default.companyId}")
	private String defaultCompanyId;
	
	@Value("${register.default.roleId}")
	private String defaultRoleId;
	
	/**
	 * pc用户注册页面
	 * @param model
	 * @return
	 */
	@RequestMapping("register")
	public String register(User user, Model model) {
		return "modules/sys/sysRegister";
	}
	
	 /**
	 * 注册方法
	 * @param user
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("doRegister")
	@ResponseBody
	public Result doRegister(User user, HttpServletRequest request, Model model){
		 //设置默认注册公司及部门
		 user.setCompany(new Office(defaultCompanyId));
		 user.setOffice(new Office(defaultCompanyId));
		 user.setEmail(user.getLoginName());
		 if (!beanValidator(model, user)){
			return ResultGenerator.genFailResult("注册失败，表单验证失败");
		 }
		 if (!"true".equals(checkLoginName(user.getLoginName()))){
			 return ResultGenerator.genFailResult("用户'" + user.getLoginName() + "'注册失败，登录名已存在");
		 }
		 if (StringUtils.isNotBlank(user.getPassword())) {
				user.setPassword(SystemService.entryptPassword(user.getPassword()));
			}
		 // 设置默认角色
		 List<Role> roleList = Lists.newArrayList();
		 Role defaultRole = new Role(defaultRoleId);
		 roleList.add(defaultRole);
		 user.setRoleList(roleList);
		 user.setUserType("3");
		 user.setLoginDate(new Date());
		 user.setLoginIp(HttpUtil.getClientIP(request));
		 user.setCreateBy(new User("1"));
		 user.setCreateDate(new Date());
		 user.setUpdateBy(new User("1"));
		 user.setUpdateDate(new Date());
		 // 保存用户信息
		 systemService.saveUser(user);
		 return ResultGenerator.genSuccessResult("恭喜您! "+user.getName()+"，注册成功");
		 //return "redirect:" + adminPath;
	}
	
	/**
	 * 验证登录名是否存在
	 * @param loginName
	 * @return
	 */
	private String checkLoginName(String loginName) {
		if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}
	
	/**
     * 执行登录请求
     *
     * @param username
     * @param request
     * @return
     */
    private String login(User user, HttpServletRequest request) {
        String ret = "redirect:" + adminPath +"/register";
        if (StringUtils.isNotBlank(user.getId())) {
        	UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(),user.getPassword().toCharArray(),false,"","",false,"9");
            try {
                SecurityUtils.getSubject().login(token);
            } catch (AuthenticationException e) {
                if (e instanceof UnknownAccountException) {
                    throw new ServiceException("用户不存在");
                } else if (e instanceof LockedAccountException) {
                    throw new ServiceException("用户被禁用");
                } else {
                    throw new ServiceException("用户认证失败");
                }
            }
            return ret;
        }
        throw new ServiceException("登录失败！");
    }
	
}
