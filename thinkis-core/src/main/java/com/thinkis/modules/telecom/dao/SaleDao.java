package com.thinkis.modules.telecom.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.telecom.entity.Sale;

@MyBatisDao
public interface SaleDao extends CrudDao<Sale> {
}
