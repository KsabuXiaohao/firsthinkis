/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.sys.dao;

import com.thinkis.common.persistence.TreeDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
