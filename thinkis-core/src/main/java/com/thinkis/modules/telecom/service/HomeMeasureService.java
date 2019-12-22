package com.thinkis.modules.telecom.service;

import com.thinkis.common.persistence.Page;
import com.thinkis.common.service.CrudService;
import com.thinkis.modules.telecom.dao.HomeMeasureDao;
import com.thinkis.modules.telecom.entity.HomeMeasure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class HomeMeasureService extends CrudService<HomeMeasureDao, HomeMeasure> {



    public List<Map<String,Object>> getWeekList(){
        return dao.getWeekList();
    }
    public List<Map<String,Object>> getTimeList(String sdate,String edate){
        return dao.getTimeList(sdate,edate);
    }
    public List<HomeMeasure> getHomeMeasure(HomeMeasure entity) {
        return dao.getHomeMeasure(entity);
    }
    public List<Map<String,Object>> getWeekListById(String id){
        return dao.getWeekListById(id);
    }

    public List<Map<String,Object>> getTimeListById(String sdate,String edate,String id){
        return dao.getTimeListById(sdate,edate,id);
    }

    public Page<HomeMeasure> findPageById(Page<HomeMeasure> page, HomeMeasure entity) {
        entity.setPage(page);
        page.setList(dao.findListById(entity));
        return page;
    }

    public Page<HomeMeasure> getNumberList(Page<HomeMeasure> page, HomeMeasure entity) {
        entity.setPage(page);
        page.setList(dao.getNumberList(entity));
        return page;
    }

    public Page<HomeMeasure> getNumberListById(Page<HomeMeasure> page, HomeMeasure entity) {
        entity.setPage(page);
        page.setList(dao.getNumberListById(entity));
        return page;
    }
}
