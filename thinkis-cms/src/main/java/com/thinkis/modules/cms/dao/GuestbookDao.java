/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.dao;

import com.thinkis.modules.cms.entity.Guestbook;
import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;

/**
 * 留言DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface GuestbookDao extends CrudDao<Guestbook> {

}
