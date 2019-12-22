package com.thinkis.modules.sys.oauth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 2018年5月14日18:18:26.
 * liuzhiping
 */
public class OauthToken implements AuthenticationToken {

	private static final long serialVersionUID = 1L;

	private String openId = null;

    private String code = null;

    private String access_token = null;

    private Integer expires_in;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public OauthToken(String openId, String code) {
        this.openId = openId;
        this.code = code;
    }

    public OauthToken(String openId) {
        this.openId = openId;
    }

    @Override
    public Object getPrincipal() {
        return openId;
    }

    @Override
    public Object getCredentials() {
        return openId;
    }
    
}
