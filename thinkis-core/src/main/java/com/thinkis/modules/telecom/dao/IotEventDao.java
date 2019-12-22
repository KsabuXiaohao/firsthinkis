package com.thinkis.modules.telecom.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.telecom.entity.IotEvent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface IotEventDao extends CrudDao<IotEvent> {

    @Select("SELECT * FROM iot_event a where device_id = #{deviceid} ORDER BY CREATED_STAMP DESC LIMIT 0,1  ")
    Map<String , Object> getDeviceid(@Param("deviceid") String deviceid);
}

