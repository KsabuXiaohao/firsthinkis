/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkis.common.oauth.util.EnumOauthTypeBean;
import com.thinkis.common.persistence.DataEntity;
import com.thinkis.modules.sys.entity.User;

import cn.hutool.core.convert.Convert;

/**
 * 认证信息Entity
 * @author 刘志平
 * @version 2017-12-25
 */
public class OpenOauth extends DataEntity<OpenOauth> {
	
	private static final long serialVersionUID = 1L;
	private String accessToken;		// 认证token
	private String expireIn;		// 过期时间
	private String oauthCode;		// 认证编码
	private String oauthType;		// 认证类型
	private String oauthUserId;		// openid
	private String refreshToken;		// 认证token
	private User user;		// 用户
	
	// extends
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    private String mobile;
    private String password;
	
	public OpenOauth() {
		super();
	}

	public OpenOauth(String id){
		super(id);
	}

	@Length(min=0, max=255, message="认证token长度必须介于 0 和 255 之间")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Length(min=0, max=255, message="过期时间长度必须介于 0 和 255 之间")
	public String getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(String expireIn) {
		this.expireIn = expireIn;
	}
	
	@Length(min=0, max=255, message="认证编码长度必须介于 0 和 255 之间")
	public String getOauthCode() {
		return oauthCode;
	}

	public void setOauthCode(String oauthCode) {
		this.oauthCode = oauthCode;
	}
	
	@Length(min=0, max=11, message="认证类型长度必须介于 0 和 11 之间")
	public String getOauthType() {
		return oauthType;
	}

	public void setOauthType(String oauthType) {
		this.oauthType = oauthType;
	}
	
	public String getOauthTypeLabel(){
		try {
			if(null!=oauthType){
				return EnumOauthTypeBean.getEnumStatus(Convert.toInt(this.oauthType)).getDescription();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oauthType;
	}
	
	@Length(min=0, max=255, message="openid长度必须介于 0 和 255 之间")
	public String getOauthUserId() {
		return oauthUserId;
	}

	public void setOauthUserId(String oauthUserId) {
		this.oauthUserId = oauthUserId;
	}
	
	@Length(min=0, max=255, message="认证token长度必须介于 0 和 255 之间")
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUserNameLabel(){
		return user.getName();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}