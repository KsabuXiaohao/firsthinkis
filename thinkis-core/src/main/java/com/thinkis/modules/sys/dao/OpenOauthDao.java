/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.sys.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.sys.entity.OpenOauth;

/**
 * 认证信息DAO接口
 * @author 刘志平
 * @version 2017-12-25
 */
@MyBatisDao
public interface OpenOauthDao extends CrudDao<OpenOauth> {
}