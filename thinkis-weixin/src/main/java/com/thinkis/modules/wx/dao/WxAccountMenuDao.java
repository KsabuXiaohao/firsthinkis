/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.wx.dao;

import com.thinkis.modules.wx.entity.WxAccountMenu;
import com.thinkis.common.persistence.TreeDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;

/**
 * 公众号菜单DAO接口
 * @author liuzhiping
 * @version 2018-04-15
 */
@MyBatisDao
public interface WxAccountMenuDao extends TreeDao<WxAccountMenu> {
	
}