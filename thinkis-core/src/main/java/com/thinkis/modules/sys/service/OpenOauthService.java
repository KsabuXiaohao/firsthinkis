/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkis.common.persistence.Page;
import com.thinkis.common.service.CrudService;
import com.thinkis.modules.sys.dao.OpenOauthDao;
import com.thinkis.modules.sys.entity.OpenOauth;

/**
 * 认证信息Service
 * @author 刘志平
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class OpenOauthService extends CrudService<OpenOauthDao, OpenOauth> {
	
	@Autowired
	private OpenOauthDao openOauthDao;

	public OpenOauth get(String id) {
		return super.get(id);
	}
	
	public OpenOauth getOauthByOauthUserId(String oauthUserId){
		OpenOauth openOauth = new OpenOauth();
		openOauth.setOauthUserId(oauthUserId);
		List<OpenOauth> list = openOauthDao.findList(openOauth);
		if(!list.isEmpty()){
			return openOauth = list.get(0);
		}else{
			return null;
		}
	}
	
	public List<OpenOauth> findList(OpenOauth openOauth) {
		return super.findList(openOauth);
	}
	
	public Page<OpenOauth> findPage(Page<OpenOauth> page, OpenOauth openOauth) {
		return super.findPage(page, openOauth);
	}
	
	@Transactional(readOnly = false)
	public void save(OpenOauth openOauth) {
		super.save(openOauth);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpenOauth openOauth) {
		super.delete(openOauth);
	}
	
	@Transactional(readOnly = false)
	public int batchDelete(String selectedIds){
		int success = 0;
		try{
			String[] ids = selectedIds.split(",");
			for(String id : ids){
				OpenOauth openOauth = new OpenOauth();
				openOauth.setId(id);
				super.delete(openOauth);
				success++;
			}
		}catch(Exception e){
			logger.error("数据删除失败");
			return success;
		}
		return success;
	}
}