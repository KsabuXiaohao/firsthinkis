/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.wx.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkis.common.config.Global;
import com.thinkis.common.service.ServiceException;
import com.thinkis.common.service.TreeService;
import com.thinkis.modules.wx.dao.WxAccountMenuDao;
import com.thinkis.modules.wx.entity.WxAccountMenu;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 公众号菜单Service
 * @author liuzhiping
 * @version 2018-04-15
 */
@Service
@Transactional(readOnly = true)
public class WxAccountMenuService extends TreeService<WxAccountMenuDao, WxAccountMenu> {

	@Autowired
	private Gzh1WxService wxService;

	public WxAccountMenu get(String id) {
		return super.get(id);
	}
	
	public List<WxAccountMenu> findList(WxAccountMenu wxAccountMenu) {
		if (StringUtils.isNotBlank(wxAccountMenu.getParentIds())){
			wxAccountMenu.setParentIds(","+wxAccountMenu.getParentIds()+",");
		}
		return super.findList(wxAccountMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(WxAccountMenu wxAccountMenu) {
		super.save(wxAccountMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxAccountMenu wxAccountMenu) {
		super.delete(wxAccountMenu);
	}

	/**
	 * 创建微信菜单
	 *
	 * @param id id
	 */
	public void createMenu(String groupId) {
		List<WxAccountMenu> lstMenu = findAllMenu(groupId);
		WxMenu wxmenu = new WxMenu();
		List<WxMenuButton> buttons = Lists.newArrayList();
		for (WxAccountMenu menu : lstMenu) {
			WxMenuButton wxButton = new WxMenuButton();
			wxButton.setName(menu.getName());
			List<WxAccountMenu> childMenus = menu.getChildList();
			if (null == childMenus || childMenus.size() == 0) {// 子菜单为空
				wxButton.setKey(menu.getEventKey());
				String type = menu.getType();
				wxButton.setType(type);
				if (WxAccountMenu.TYPE_VIEW.equals(type)) {
					if(menu.getOauth().equals(Global.YES)){
						wxButton.setUrl(wxService.oauth2buildAuthorizationUrl(menu.getViewUrl(),"snsapi_userinfo","STATE"));
					}else{
						wxButton.setUrl(menu.getViewUrl());
					}
				}
			} else {
				List<WxMenuButton> subButtons = Lists.newArrayList();
				for (WxAccountMenu cmenu : childMenus) {
					WxMenuButton subButton = new WxMenuButton();
					subButton.setName(cmenu.getName());
					subButton.setKey(cmenu.getEventKey());
					String cType = cmenu.getType();
					subButton.setType(cType);
					if(cmenu.getOauth().equals(Global.YES)){
						subButton.setUrl(wxService.oauth2buildAuthorizationUrl(cmenu.getViewUrl(),"snsapi_userinfo","STATE"));
					}else{
						subButton.setUrl(cmenu.getViewUrl());
					}
					subButtons.add(subButton);
				}
				wxButton.setSubButtons(subButtons);
			}
			buttons.add(wxButton);
		}
		wxmenu.setButtons(buttons);
		try {
			wxService.getMenuService().menuCreate(wxmenu);
		} catch (WxErrorException e) {
			throw new ServiceException("创建微信菜单异常", e);
		}
	}
	/**
	 * 查询1级菜单
	 *
	 * @param groupId groupId
	 * @return menus
	 */
	public List<WxAccountMenu> findAllMenu(String groupId) {
		WxAccountMenu entity = new WxAccountMenu();
		entity.setGroupId(groupId);
		List<WxAccountMenu> lstMenu = super.findList(entity);
		Map<String, WxAccountMenu> map = Maps.newHashMap();
		for (WxAccountMenu menu : lstMenu) {
			String id = menu.getId();
			map.put(id, menu);
		}
		for (WxAccountMenu menu : lstMenu) {
			String parentId = menu.getParent().getId();
			if("0".equals(parentId)){
				continue;
			}
			WxAccountMenu parent = map.get(parentId);
			if(null!=parent){
				parent.addChildMenu(menu);
			}
		}
		List<WxAccountMenu> lstResult = Lists.newArrayList();
		for (WxAccountMenu menu : lstMenu) {
			String parentId = menu.getParent().getId();
			if ("0".equals(parentId)) {
				lstResult.add(menu);
			}
		}

		return lstResult;
	}
	
}