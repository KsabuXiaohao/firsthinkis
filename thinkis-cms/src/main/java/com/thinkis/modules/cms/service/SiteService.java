/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkis.modules.cms.dao.SiteDao;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.utils.CmsUtils;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.service.CrudService;

import java.util.List;

/**
 * 站点Service
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true)
public class SiteService extends CrudService<SiteDao, Site> {

	public Site getByTheme(String theme){
		return super.dao.getByTheme(theme);
	}

	public Page<Site> findPage(Page<Site> page, Site site) {
//		DetachedCriteria dc = siteDao.createDetachedCriteria();
//		if (StringUtils.isNotEmpty(site.getName())){
//			dc.add(Restrictions.like("name", "%"+site.getName()+"%"));
//		}
//		dc.add(Restrictions.eq(Site.FIELD_DEL_FLAG, site.getDelFlag()));
//		//dc.addOrder(Order.asc("id"));
//		return siteDao.find(page, dc);
		
		site.getSqlMap().put("site", dataScopeFilter(site.getCurrentUser(), "o", "u"));
		
		return super.findPage(page, site);
	}

	@Transactional(readOnly = false)
	public void save(Site site) {
		if (site.getCopyright()!=null){
			site.setCopyright(StringEscapeUtils.unescapeHtml4(site.getCopyright()));
		}
		super.save(site);
		CmsUtils.removeCache("site_"+site.getId());
		CmsUtils.removeCache("siteList");
	}
	
	@Transactional(readOnly = false)
	public void delete(Site site, Boolean isRe) {
		//siteDao.updateDelFlag(id, isRe!=null&&isRe?Site.DEL_FLAG_NORMAL:Site.DEL_FLAG_DELETE);
		site.setDelFlag(isRe!=null&&isRe?Site.DEL_FLAG_NORMAL:Site.DEL_FLAG_DELETE);
		super.delete(site);
		//siteDao.delete(id);
		CmsUtils.removeCache("site_"+site.getId());
		CmsUtils.removeCache("siteList");
	}

	@Transactional(readOnly = false)
	public void setDefaultSite(String id){
		List<Site> sites = findList(new Site());
		for(Site site : sites){
			site.setDomain(Site.NOT_DEFAULT_DOMAIN);
			save(site);
		}
		Site defaultSite = get(id);
		if(null!=defaultSite){
			defaultSite.setDomain(Site.DEFAULT_DOMAIN);
			save(defaultSite);
		}
	}
	
}
