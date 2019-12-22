package com.thinkis.modules.telecom.service;

import com.thinkis.common.service.CrudService;
import com.thinkis.modules.telecom.dao.IndoorMeasureDao;
import com.thinkis.modules.telecom.entity.IndoorMeasure;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class IndoorMeasureService extends CrudService<IndoorMeasureDao, IndoorMeasure> {
}
