/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.dao;

import java.util.List;

import com.thinkis.modules.cms.entity.Article;
import com.thinkis.modules.cms.entity.Category;
import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;

/**
 * 文章DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {
	
	public List<Article> findByIdIn(String[] ids);
//	{
//		return find("from Article where id in (:p1)", new Parameter(new Object[]{ids}));
//	}
	
	public int updateHitsAddOne(String id);
//	{
//		return update("update Article set hits=hits+1 where id = :p1", new Parameter(id));
//	}
	
	public int updateExpiredWeight(Article article);
	
	public List<Category> findStats(Category category);
//	{
//		return update("update Article set weight=0 where weight > 0 and weightDate < current_timestamp()");
//	}
	
}
