/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.wx.dao;

import com.thinkis.modules.wx.entity.WxAccountFans;
import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;

/**
 * 公众号关注人DAO接口
 * @author liuzhiping
 * @version 2018-04-13
 */
@MyBatisDao
public interface WxAccountFansDao extends CrudDao<WxAccountFans> {
	
}