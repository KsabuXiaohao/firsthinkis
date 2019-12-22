/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.dao;

import com.thinkis.modules.cms.entity.Comment;
import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface CommentDao extends CrudDao<Comment> {
    List<Comment> hotsArticles(@Param("siteId") String siteId,@Param("number")  int number);
}
