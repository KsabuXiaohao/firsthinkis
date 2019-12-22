package com.thinkis.modules.telecom.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.telecom.entity.Device;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DeviceDao extends CrudDao<Device> {

    @Select("SELECT count(1) FROM device where meid = #{meid} ")
    int getMeid(@Param("meid") String meid);
    @Update("update device set companyid = #{companyid},updatetime = now(),company=#{company},sim=#{sim},imei=${imei},imsi=#{imsi}," +
            "longitude=#{longitude},latitude=#{latitude},position=#{position},deviceid=#{deviceid},sendaddress=#{sendaddress},sendtype=#{sendtype}  where id = #{id} ")
    int setCompanyid(@Param("companyid") String companyid , @Param("meid") String meid , @Param("company") String company ,
                     @Param("sim") String sim , @Param("imei") String imei,@Param("imsi") String imsi,@Param("longitude") String longitude,
                     @Param("latitude") String latitude,@Param("position") String position,@Param("deviceid") String deviceid,@Param("sendaddress") String sendaddress,@Param("sendtype") String sendtype,@Param("id") String id);
    @Select("SELECT * FROM device LIMIT 1 ")
    Device getDeviceDemo();
    @Insert("insert into device (meid,companyid,company,isused,updatetime,addtime,sim,imei,imsi,longitude,latitude,position,deviceid,sendaddress,sendtype)" +
            "values(#{meid},#{companyid},#{company},1,now(),now(),#{sim},#{imei},#{imsi},#{longitude},#{latitude},#{position},#{deviceid},#{sendaddress},#{sendtype})")
    int saveCompanyid( @Param("meid") String meid , @Param("companyid") String companyid , @Param("company") String company,
                       @Param("sim") String sim,@Param("imei") String imei,@Param("imsi") String imsi,@Param("longitude") String longitude,
                       @Param("latitude") String latitude,@Param("position") String position,@Param("deviceid") String deviceid,@Param("sendaddress") String sendaddress,@Param("sendtype") String sendtype);

    @Select("select a.imei, a.deviceid,a.company,a.position,a.longitude,a.latitude,b.ALARM,b.CREATED_STAMP,a.isonline" +
            " from device a,iot_event b where a.deviceid = b.DEVICE_ID  and b.CREATED_STAMP in ( " +
            "select Max(b.CREATED_STAMP) as CREATED_STAMP from device a,iot_event b where a.deviceid = b.DEVICE_ID GROUP BY a.deviceid)  ")
    List<Map<String,Object>> getWeekList();

    @Select("select a.imei, a.deviceid,a.company,a.position,a.longitude,a.latitude,b.ALARM,b.CREATED_STAMP,a.isonline from device a,iot_event b " +
            "where a.deviceid = b.DEVICE_ID  and a.addtime >= #{sdate} and a.addtime <= #{edate}  and b.CREATED_STAMP in (" +
            "select Max(b.CREATED_STAMP) as CREATED_STAMP from device a,iot_event b where a.deviceid = b.DEVICE_ID GROUP BY a.deviceid)")
    List<Map<String,Object>> getTimeList(@Param("sdate") String sdate,@Param("edate") String edate);

    @Select("select a.imei, a.deviceid,a.company,a.position,a.longitude,a.latitude,b.ALARM,b.CREATED_STAMP,a.isonline from device a,iot_event b " +
            "where a.deviceid = b.DEVICE_ID  and a.companyid = #{id}  and b.CREATED_STAMP in (" +
            "select Max(b.CREATED_STAMP) as CREATED_STAMP from device a,iot_event b where a.deviceid = b.DEVICE_ID GROUP BY a.deviceid)")
    List<Map<String,Object>> getWeekListById(@Param("id") String id);

    @Select("select a.imei, a.deviceid,a.company,a.position,a.longitude,a.latitude,b.ALARM,b.CREATED_STAMP,a.isonline from device a,iot_event b " +
            "where a.deviceid = b.DEVICE_ID  and a.addtime >= #{sdate} and a.addtime <= #{edate} and a.companyid = #{id}  and b.CREATED_STAMP in (" +
            "select Max(b.CREATED_STAMP) as CREATED_STAMP from device a,iot_event b where a.deviceid = b.DEVICE_ID GROUP BY a.deviceid)")
    List<Map<String,Object>> getTimeListById(@Param("sdate") String sdate,@Param("edate") String edate,@Param("id") String id);


    List<Device> getDeviceByCompanyId(Device device);
    List<Device> getAlarmList(Device device);
    List<Device> getkhAlarmList(Device device);
    List<Device> findListHistory(Device device);
    List<Device> findListkhHistory(Device device);
    List<Device> chartHistory(Device device);
    List<Device> chartkhHistory(Device device);
    List<Device> findImeihHistory(Device device);
    List<Map<String,Object>> chartImeihHistory(Device device);
    List<Device> importImeiHistory(Device device);
    List<Device> importDeviceHistory(Device device);
    List<Device> importDevicekhHistory(Device device);
}
