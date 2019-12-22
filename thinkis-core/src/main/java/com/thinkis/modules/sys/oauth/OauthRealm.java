package com.thinkis.modules.sys.oauth;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkis.common.config.Global;
import com.thinkis.modules.sys.entity.OpenOauth;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.service.OpenOauthService;
import com.thinkis.modules.sys.service.SystemService;

/**
 *  2018年5月14日18:18:26.
 *  liuzhiping
 */
@Service
public class OauthRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(OauthRealm.class);

    @Autowired
    private SystemService systemService;
    
    @Autowired
    private OpenOauthService openOauthService;

    public Class<OauthToken> getAuthenticationTokenClass() {
        return OauthToken.class;
    }
    
    @Override
    public boolean supports(AuthenticationToken token) { 
    	return token != null && getAuthenticationTokenClass().isAssignableFrom(token.getClass()); 
	}
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try {
            OauthToken token = (OauthToken) authenticationToken;
            if (StringUtils.isNotBlank(token.getCode())&&StringUtils.isNotBlank(token.getOpenId())) {
            	 OpenOauth thirdToken = openOauthService.getOauthByOauthUserId(token.getOpenId());
                 User user = systemService.getUser(thirdToken.getUser().getId());
                 if (user != null) {
                    if (Global.NO.equals(user.getLoginFlag())) {
                        logger.warn("该帐号[" + user.getId() + "]禁止登录.");
                        throw new AuthenticationException("msg:该帐号禁止登录.");
                    }
                    return new SimpleAuthenticationInfo(user.getId(),token.getOpenId(), getName());
                } else {
                	throw new UnknownAccountException("msg:用户不存在");
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("认证异常", e);
        }
        return null;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
