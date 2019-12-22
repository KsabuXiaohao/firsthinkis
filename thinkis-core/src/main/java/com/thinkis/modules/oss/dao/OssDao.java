/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.oss.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.oss.entity.Oss;

/**
 * 附件管理DAO接口
 * @author liuzhiping
 * @version 2018-05-09
 */
@MyBatisDao
public interface OssDao extends CrudDao<Oss> {
	
}