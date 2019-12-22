package com.thinkis.modules.telecom.service;

import com.thinkis.common.persistence.Page;
import com.thinkis.common.service.CrudService;
import com.thinkis.common.utils.IdGen;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.utils.UserUtils;
import com.thinkis.modules.telecom.dao.DeviceDao;
import com.thinkis.modules.telecom.entity.Device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DeviceService extends CrudService<DeviceDao, Device> {

    public int getMeid(String meid){
        return dao.getMeid(meid);
    }

    public Device getDeviceDemo(){
        return dao.getDeviceDemo();
    }

    public Page<Device> getDeviceByCompanyId(Page<Device> page,Device entity){
        User user = UserUtils.getUser();
        entity.setCompanyid(user.getCompany().getId());
        entity.setPage(page);
        page.setList(dao.getDeviceByCompanyId(entity));
        return page;
    }


    @Transactional(readOnly = false)
    public int saveCompanyid( Device device){
        return dao.saveCompanyid(device.getMeid(),device.getCompanyid(),device.getCompany(),device.getSim(),device.getImei(),device.getImsi(),device.getLongitude(),device.getLatitude(),device.getPosition(),device.getDeviceid(),device.getSendaddress(),device.getSendtype());
    }


    @Transactional(readOnly = false)
    public int setCompanyid(Device device){
        return dao.setCompanyid(device.getCompanyid(),device.getMeid(),device.getCompany(),device.getSim(),device.getImei(),device.getImsi(),device.getLongitude(),device.getLatitude(),device.getPosition(),device.getDeviceid(),device.getSendaddress(),device.getSendtype(),device.getId());
    }

    public List<Map<String,Object>> getWeekList(){
        return dao.getWeekList();
    }
    public List<Map<String,Object>> getTimeList(String sdate,String edate){
        return dao.getTimeList(sdate,edate);
    }

    public List<Map<String,Object>> getWeekListById(String id){
        return dao.getWeekListById(id);
    }

    public List<Map<String,Object>> getTimeListById(String sdate,String edate,String id){
        return dao.getTimeListById(sdate,edate,id);
    }

    public Page<Device> getAlarmList(Page<Device> page,Device entity){
        entity.setPage(page);
        page.setList(dao.getAlarmList(entity));
        return page;
    }
    public Page<Device> getkhAlarmList(Page<Device> page,Device entity){
        entity.setPage(page);
        page.setList(dao.getkhAlarmList(entity));
        return page;
    }

    public Page<Device> findListHistory(Page<Device> page,Device entity){
        entity.setPage(page);
        page.setList(dao.findListHistory(entity));
        return page;
    }
    public Page<Device> findListkhHistory(Page<Device> page,Device entity){
        entity.setPage(page);
        page.setList(dao.findListkhHistory(entity));
        return page;
    }
    public Page<Device> chartHistory(Page<Device> page,Device entity){
        entity.setPage(page);
        page.setList(dao.chartHistory(entity));
        return page;
    }
    public Page<Device> chartkhHistory(Page<Device> page,Device entity){
        entity.setPage(page);
        page.setList(dao.chartkhHistory(entity));
        return page;
    }
    public Page<Device> findImeihHistory(Page<Device> page,Device entity){
        entity.setPage(page);
        page.setList(dao.findImeihHistory(entity));
        return page;
    }
    public List<Map<String,Object>> chartImeihHistory(Device entity){
        return dao.chartImeihHistory(entity);
    }
    public List<Device> importImeiHistory(Device device){
        return dao.importImeiHistory(device);
    }
    public List<Device> importDeviceHistory(Device device){
        return dao.importDeviceHistory(device);
    }
    public List<Device> importDevicekhHistory(Device device){
        return dao.importDevicekhHistory(device);
    }
}
