/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.gen.dao;

import com.thinkis.modules.gen.entity.GenScheme;
import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;

/**
 * 生成方案DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {
	
}
