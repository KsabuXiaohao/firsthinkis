package com.thinkis.modules.telecom.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.telecom.entity.OutdoorMeasure;

@MyBatisDao
public interface OutdoorMeasureDao extends CrudDao<OutdoorMeasure> {
}
