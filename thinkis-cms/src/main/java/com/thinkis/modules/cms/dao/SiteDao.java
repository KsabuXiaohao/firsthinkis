/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.dao;

import com.thinkis.modules.cms.entity.Site;
import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;

/**
 * 站点DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface SiteDao extends CrudDao<Site> {

    Site getByTheme(String theme);
}
