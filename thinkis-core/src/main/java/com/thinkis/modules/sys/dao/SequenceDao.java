/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.sys.dao;

import org.apache.ibatis.annotations.Select;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.sys.entity.Sequence;

/**
 * 序列键管理DAO接口
 * @author liuzhiping
 * @version 2017-08-01
 */
@MyBatisDao
public interface SequenceDao extends CrudDao<Sequence> {
	@Select("SELECT TH_NEXTSEQ(#{keyName,jdbcType=VARCHAR}) FROM DUAL")
	String getKey(String keyName);
}