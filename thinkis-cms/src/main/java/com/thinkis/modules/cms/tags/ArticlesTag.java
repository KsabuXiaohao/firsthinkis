package com.thinkis.modules.cms.tags;

import cn.hutool.core.convert.Convert;
import com.thinkis.common.config.Global;
import com.thinkis.common.mapper.JsonMapper;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.SpringContextHolder;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.modules.cms.entity.Article;
import com.thinkis.modules.cms.entity.Category;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.service.ArticleService;
import com.thinkis.modules.sys.utils.UserUtils;
import org.beetl.core.GeneralVarTagBinding;

import java.util.Map;

/**
 * 获取文章列表
 *  siteId 站点编号
 *  categoryId 分类编号
 *  count 获取数目
 *  param  预留参数，例： key1:'value1', key2:'value2' ...
 * 			posid	推荐位（1：首页焦点图；2：栏目页文章推荐；）
 * 			image	文章图片（1：有图片的文章）
 *          orderBy 排序字符串
 * @return
 * ${fnc:getArticleList(category.site.id, category.id, not empty pageSize?pageSize:8, 'posid:2, orderBy: \"hits desc\"')}"
 */
public class ArticlesTag extends GeneralVarTagBinding {

    private static ArticleService articleService = SpringContextHolder.getBean(ArticleService.class);

    @Override
    public void render(){

        String siteId = (String)this.getAttributeValue("siteId");
        String categoryId = (String)this.getAttributeValue("categoryId");
        int count = Convert.toInt(this.getAttributeValue("count"));
        String param = (String)this.getAttributeValue("param");

        Article article = new Article();
        Category category = new Category(categoryId, new Site(siteId));
        category.setParentIds(categoryId);
        article.setCategory(category);

        Page<Article> page = new Page<Article>(1, count, -1);

        if (StringUtils.isNotBlank(param)){
            @SuppressWarnings({ "rawtypes" })
            Map map = JsonMapper.getInstance().fromJson("{"+param+"}", Map.class);
            if (new Integer(1).equals(map.get("posid")) || new Integer(2).equals(map.get("posid"))){
                article.setPosid(String.valueOf(map.get("posid")));
            }
            if (new Integer(1).equals(map.get("image"))){
                article.setImage(Global.YES);
            }
            if (new Integer(1).equals(map.get("myArticles"))){
                article.setCreateBy(UserUtils.getUser());
            }
            if (StringUtils.isNotBlank((String)map.get("orderBy"))){
                page.setOrderBy((String)map.get("orderBy"));
            }
        }
        article.setDelFlag(Article.DEL_FLAG_NORMAL);
        page = articleService.findPage(page, article, false);

        for(Article obj : page.getList()){
            this.binds(obj);
            this.doBodyRender();
        }
    }
}
