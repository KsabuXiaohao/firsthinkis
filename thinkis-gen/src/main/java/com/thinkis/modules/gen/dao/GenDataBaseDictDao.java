/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.gen.dao;

import java.util.List;

import com.thinkis.modules.gen.entity.GenTable;
import com.thinkis.modules.gen.entity.GenTableColumn;
import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;

/**
 * 业务表字段DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenDataBaseDictDao extends CrudDao<GenTableColumn> {

	/**
	 * 查询表列表
	 * @param genTable
	 * @return
	 */
	List<GenTable> findTableList(GenTable genTable);

	/**
	 * 获取数据表字段
	 * @param genTable
	 * @return
	 */
	List<GenTableColumn> findTableColumnList(GenTable genTable);
	
	/**
	 * 获取数据表主键
	 * @param genTable
	 * @return
	 */
	List<String> findTablePK(GenTable genTable);
	
}
