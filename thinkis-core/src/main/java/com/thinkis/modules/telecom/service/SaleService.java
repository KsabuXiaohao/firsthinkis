package com.thinkis.modules.telecom.service;

import com.thinkis.common.service.CrudService;
import com.thinkis.modules.telecom.dao.SaleDao;
import com.thinkis.modules.telecom.entity.Sale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SaleService extends CrudService<SaleDao, Sale> {
}
