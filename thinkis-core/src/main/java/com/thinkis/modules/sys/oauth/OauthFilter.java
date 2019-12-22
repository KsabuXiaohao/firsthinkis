package com.thinkis.modules.sys.oauth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

/**
 *  2018年5月14日18:18:26.
 *  liuzhiping
 */
public class OauthFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            return subject.getPrincipal() != null;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

}
