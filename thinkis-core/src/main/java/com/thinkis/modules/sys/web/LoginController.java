/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.thinkis.common.config.Global;
import com.thinkis.common.security.shiro.session.SessionDAO;
import com.thinkis.common.servlet.ValidateCodeServlet;
import com.thinkis.common.utils.CacheUtils;
import com.thinkis.common.utils.CookieUtils;
import com.thinkis.common.utils.IdGen;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.security.FormAuthenticationFilter;
import com.thinkis.modules.sys.security.LoginType;
import com.thinkis.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {

		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(UserUtils.getSubject().isAuthenticated()){
			User user = UserUtils.getUser();
			String loginType = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_LOGINTYPE_PARAM);
			if(isNormalUser(user)||LoginType.USER.toString().equals(loginType)){
				return "redirect:" + Global.getConfig("web.view.index");
			}else{
				return "redirect:" + adminPath;
			}
		}
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		String loginType = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_LOGINTYPE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 如果已经登录，则跳转到管理首页
		if(UserUtils.getSubject().isAuthenticated()){
			User user = UserUtils.getUser();
			if(isNormalUser(user)||LoginType.USER.toString().equals(loginType)){
				return "redirect:" + Global.getConfig("web.view.index");
			}else{
				return "redirect:" + adminPath;
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
	        return renderString(response, model);
		}
		
		if(StringUtils.isNotBlank(loginType) && LoginType.USER.toString().equals(loginType)){
			return "redirect:" + frontPath + "/login.html"; 
		}
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
		User user = UserUtils.getUser();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(user.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, " ");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		//如果是普通用户，或者前端登录，则跳转到前端首页，否则跳转到管理员首页
		String loginType = request.getParameter(FormAuthenticationFilter.DEFAULT_LOGINTYPE_PARAM);
		if(isNormalUser(user)||LoginType.USER.toString().equals(loginType)){
			return "modules/sys/sysIndex";
		}else{
			return "modules/sys/sysIndex";
		}
	}
	
	/**
	 * 判断用户是否为普通用户
	 * @param user 当前用户
	 * @return 
	 */
	private boolean isNormalUser(User user){
		boolean flag = true;
		if(null!=user && StringUtils.isNotBlank(user.getUserType())){
			String userType = user.getUserType();
			if("9".equals(userType)){
				flag = true;
			}else{
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			CacheUtils.remove("loginFailMap");
		}
		return loginFailNum >= 3;
	}
	
	/**
	 * 桌面首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/desktop")
	public String desktop(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/sysDesktop";
	}

	/**
	 * 配置信息展示页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "${adminPath}/config")
	public String config(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/sysConfig";
	}

}
