/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.wx.entity;

import java.util.Date;

import com.thinkis.modules.wx.config.WxAccountEnum;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkis.common.persistence.DataEntity;
import com.thinkis.modules.sys.utils.DictUtils;

/**
 * 公众号关注人Entity
 * @author liuzhiping
 * @version 2018-04-13
 */
public class WxAccountFans extends DataEntity<WxAccountFans> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// openID
	private String subscribeStatus;		// 关注状态
	private Date subscribeTime;		// 关注时间
	private String nickname;		// 昵称
	private String sex;		// 性别
	private String sexDesc;		// 性别描述
	private String language;		// 语言
	private String country;		// 国家
	private String province;		// 省
	private String city;		// 市
	private String headimgurl;		// 头像
	private Date createTime;		// 创建时间
	private String status;		// 状态
	private String unionId;		// 用户单元
	private String groupId;		// 用户群组
	private String remark;		// 描述
	private String wxid;		// 微信id
	private String pubid;       //公众号编号
	
	public WxAccountFans() {
		super();
		this.pubid = Integer.toString(WxAccountEnum.GZH1.getPubid());
	}

	public WxAccountFans(String id){
		super(id);
	}

	@Length(min=0, max=100, message="openID长度必须介于 0 和 100 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=10, message="关注状态长度必须介于 0 和 10 之间")
	public String getSubscribeStatus() {
		return subscribeStatus;
	}

	public void setSubscribeStatus(String subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	
	@Length(min=0, max=50, message="昵称长度必须介于 0 和 50 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=10, message="性别描述长度必须介于 0 和 10 之间")
	public String getSexDesc() {
		return sexDesc;
	}

	public void setSexDesc(String sexDesc) {
		this.sexDesc = sexDesc;
	}
	
	@Length(min=0, max=50, message="语言长度必须介于 0 和 50 之间")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Length(min=0, max=30, message="国家长度必须介于 0 和 30 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=30, message="省长度必须介于 0 和 30 之间")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Length(min=0, max=30, message="市长度必须介于 0 和 30 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=255, message="头像长度必须介于 0 和 255 之间")
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusLabel(){
		return DictUtils.getDictLabel(this.status,"yes_no","是");
	}
	
	@Length(min=0, max=20, message="用户单元长度必须介于 0 和 20 之间")
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	
	@Length(min=0, max=20, message="用户群组长度必须介于 0 和 20 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Length(min=0, max=50, message="描述长度必须介于 0 和 50 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=64, message="微信id长度必须介于 0 和 64 之间")
	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getPubid() {
		return pubid;
	}

	public void setPubid(String pubid) {
		this.pubid = pubid;
	}
	
}