package com.thinkis.modules.telecom.service;


import com.thinkis.common.persistence.Page;
import com.thinkis.common.service.CrudService;
import com.thinkis.modules.sys.utils.UserUtils;
import com.thinkis.modules.telecom.dao.IotEventDao;
import com.thinkis.modules.telecom.entity.IotEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class IotEventService extends CrudService<IotEventDao,IotEvent> {
    public Page<IotEvent> getPageList(Page<IotEvent> page, IotEvent entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }

    public Map<String,Object> getDeviceid(String deviceid){
        return  dao.getDeviceid(deviceid);
    }
}
