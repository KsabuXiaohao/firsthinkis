package com.thinkis.modules.sys.oauth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkis.common.utils.StringUtils;

/**
 * 2018年5月14日18:18:26.
 * liuzhiping
 */
public class OauthAuthcFilter extends AuthenticatingFilter {

    private static final Logger logger = LoggerFactory.getLogger(OauthFilter.class);

    private static final String CODE_PARAMETER = "code";
    
    private static final String OPENID_PARAMETER = "openId";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String code = httpRequest.getParameter(CODE_PARAMETER);
        String openId = httpRequest.getParameter(OPENID_PARAMETER);
        logger.debug("openId="+openId);
        logger.debug("code="+code);
        return new OauthToken(openId, code);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request, ServletResponse response) {
    	String className = ae.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)|| UnknownAccountException.class.getName().equals(className)){
			message = "用户或密码错误, 请重试.";
		}else if (ae.getMessage() != null && StringUtils.startsWith(ae.getMessage(), "msg:")){
			message = StringUtils.replace(ae.getMessage(), "msg:", "");
		}else{
			message = "系统出现点问题，请稍后再试！";
			ae.printStackTrace(); // 输出到控制台
		}
		logger.error(message);
        return true;
    }
}
