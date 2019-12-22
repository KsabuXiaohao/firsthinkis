/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkis.common.service.CrudService;
import com.thinkis.common.utils.CacheUtils;
import com.thinkis.modules.sys.dao.DictDao;
import com.thinkis.modules.sys.entity.Dict;
import com.thinkis.modules.sys.utils.DictUtils;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public int batchDelete(String selectedIds){
		int success = 0;
		try{
			String[] ids = selectedIds.split(",");
			for(String id : ids){
				Dict dict = new Dict();
				dict.setId(id);
				super.delete(dict);
				success++;
			}
		}catch(Exception e){
			logger.error("数据删除失败");
			return success;
		}
		return success;
	}

}
