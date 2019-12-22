/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkis.common.persistence.Page;
import com.thinkis.common.service.CrudService;
import com.thinkis.modules.sys.dao.SequenceDao;
import com.thinkis.modules.sys.entity.Sequence;

/**
 * 序列键管理Service
 * @author liuzhiping
 * @version 2017-08-01
 */
@Service
@Transactional(readOnly = true)
public class SequenceService extends CrudService<SequenceDao, Sequence> {

	public Sequence get(String id) {
		return super.get(id);
	}
	
	public List<Sequence> findList(Sequence sequence) {
		return super.findList(sequence);
	}
	
	public Page<Sequence> findPage(Page<Sequence> page, Sequence sequence) {
		return super.findPage(page, sequence);
	}
	
	@Transactional(readOnly = false)
	public void save(Sequence sequence) {
		super.save(sequence);
	}
	
	@Transactional(readOnly = false)
	public void delete(Sequence sequence) {
		super.delete(sequence);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String selectedIds){
		int success = 0;
		try{
			String[] ids = selectedIds.split(",");
			for(String id : ids){
				Sequence sequence = new Sequence();
				sequence.setName(id);
				super.delete(sequence);
				success++;
			}
		}catch(Exception e){
			logger.error("数据删除失败");
			return success;
		}
		return success;
	}


}