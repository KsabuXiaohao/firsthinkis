package com.thinkis.modules.telecom.dao;


import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.telecom.entity.IotToken;

@MyBatisDao
public interface IotTokenDao extends CrudDao<IotToken> {


}
