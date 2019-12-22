/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkis.modules.cms.dao.ArticleDataDao;
import com.thinkis.modules.cms.entity.ArticleData;
import com.thinkis.common.service.CrudService;

/**
 * 站点Service
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
