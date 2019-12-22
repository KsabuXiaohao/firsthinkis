/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.wx.entity;

import java.util.List;

import com.thinkis.modules.sys.utils.DictUtils;
import com.thinkis.modules.wx.config.WxAccountEnum;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.collect.Lists;
import com.thinkis.common.config.Global;
import com.thinkis.common.persistence.TreeEntity;

/**
 * 公众号菜单Entity
 * @author liuzhiping
 * @version 2018-04-15
 */
public class WxAccountMenu extends TreeEntity<WxAccountMenu> {
	
	private static final long serialVersionUID = 1L;
	private String eventKey;		// event_key
	private String name;		// name
	private WxAccountMenu parent;		// parent_id
	private String parentIds;		// parent_ids
	private Integer sort;		// sort
	private String type;		// type
	private String oauth;		// 是否需要授权访问
	private String viewUrl;		// view_url
	private String groupId;		// group_id
	private String msgId;		// msg_id
	private String pubid;       //公众号编号

	/** 子菜单列表 */
	private List<WxAccountMenu> childList;

	public List<WxAccountMenu> getChildList() {
		return childList;
	}

	public void setChildList(List<WxAccountMenu> childList) {
		this.childList = childList;
	}

	/** 事件类型 */
	public static final String TYPE_CLICK = "click";

	/** URL类型 */
	public static final String TYPE_VIEW = "view";
	
	public WxAccountMenu() {
		super();
		this.oauth = Global.NO;
		this.pubid = Integer.toString(WxAccountEnum.GZH1.getPubid());
	}

	public WxAccountMenu(String id){
		super(id);
	}

	@Length(min=0, max=40, message="event_key长度必须介于 0 和 40 之间")
	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
	@Length(min=0, max=50, message="name长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonBackReference
	public WxAccountMenu getParent() {
		return parent;
	}

	public void setParent(WxAccountMenu parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=20, message="type长度必须介于 0 和 20 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=1, message="是否需要授权访问长度必须介于 0 和 1 之间")
	public String getOauth() {
		return oauth;
	}

	public void setOauth(String oauth) {
		this.oauth = oauth;
	}
	
	@Length(min=0, max=2000, message="view_url长度必须介于 0 和 2000 之间")
	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	
	@Length(min=0, max=40, message="group_id长度必须介于 0 和 40 之间")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Length(min=0, max=250, message="msg_id长度必须介于 0 和 250 之间")
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	/**
	 * 添加子菜单
	 *
	 * @param menu menu
	 */
	public void addChildMenu(WxAccountMenu menu) {
		if (null == childList) {
			childList = Lists.newArrayList();
		}
		childList.add(menu);
	}

	public String getPubid() {
		return pubid;
	}

	public void setPubid(String pubid) {
		this.pubid = pubid;
	}

	public String getOauthLabel() {
		return DictUtils.getDictLabel(this.oauth, "yes_no", "");
	}
}