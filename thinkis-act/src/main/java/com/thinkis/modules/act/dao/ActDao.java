/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.act.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.act.entity.Act;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
}
