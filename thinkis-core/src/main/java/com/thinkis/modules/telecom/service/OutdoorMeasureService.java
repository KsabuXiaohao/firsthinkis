package com.thinkis.modules.telecom.service;

import com.thinkis.common.service.CrudService;
import com.thinkis.modules.telecom.dao.OutdoorMeasureDao;
import com.thinkis.modules.telecom.entity.OutdoorMeasure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OutdoorMeasureService extends CrudService<OutdoorMeasureDao, OutdoorMeasure> {
}
