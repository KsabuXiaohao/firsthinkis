/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.oa.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyDao extends CrudDao<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify);
	
}