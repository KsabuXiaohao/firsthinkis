/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.oss.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkis.common.persistence.Page;
import com.thinkis.common.service.CrudService;
import com.thinkis.modules.oss.dao.OssDao;
import com.thinkis.modules.oss.entity.Oss;

/**
 * 附件管理Service
 * @author liuzhiping
 * @version 2018-05-09
 */
@Service
@Transactional(readOnly = true)
public class OssService extends CrudService<OssDao, Oss> {
	
	public Oss get(String id) {
		return super.get(id);
	}
	
	public List<Oss> findList(Oss oss) {
		return super.findList(oss);
	}
	
	public Page<Oss> findPage(Page<Oss> page, Oss oss) {
		return super.findPage(page, oss);
	}
	
	@Transactional(readOnly = false)
	public void save(Oss oss) {
		super.save(oss);
	}
	
	@Transactional(readOnly = false)
	public void delete(Oss oss) {
		super.delete(oss);
	}
	
	@Transactional(readOnly = false)
	public int batchDelete(String selectedIds){
		int success = 0;
		try{
			String[] ids = selectedIds.split(",");
			for(String id : ids){
				Oss oss = new Oss();
				oss.setId(id);
				super.delete(oss);
				success++;
			}
		}catch(Exception e){
			logger.error("数据删除失败");
			return success;
		}
		return success;
	}
	
}