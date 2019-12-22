package com.thinkis.modules.app.api.cms;

import com.thinkis.common.web.R;
import com.thinkis.modules.app.form.CmsForm;
import com.thinkis.modules.cms.entity.Article;
import com.thinkis.modules.cms.entity.Category;
import com.thinkis.modules.cms.entity.Link;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.service.ArticleDataService;
import com.thinkis.modules.cms.utils.CmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * cms接口
 *
 * @author liuzhiping
 * @date 2018年9月15日 12点33分
 */
@RestController
@RequestMapping("${apiPath}/api/cms")
@Api("cms接口")
public class CmsApi {

    @Autowired
    private ArticleDataService articleDataService;

    @RequestMapping(value = "queryAllAdImages",method = RequestMethod.POST)
    @ApiOperation("获取广告图片")
    public R queryAllAdImages(@RequestBody CmsForm form){
        List<Link> links =  CmsUtils.getLinkList(form.getSiteId(),form.getCategoryId(),5,"");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("obj", links);
        return R.ok(map);
    }

    @RequestMapping(value = "getMenuList",method = RequestMethod.POST)
    @ApiOperation("获取栏目信息")
    public R getMenuList(@RequestBody CmsForm form){
        List<Category> categorys = CmsUtils.getMiniAppSubMainNavList(form.getSiteId(),form.getParentId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("obj", categorys);
        return R.ok(map);
    }

    @RequestMapping(value = "articles",method = RequestMethod.POST)
    @ApiOperation("根据ColumnId返回文章数据")
    public R articles(@RequestBody CmsForm form){
        List<Article> articles = CmsUtils.getArticleList(form.getSiteId(),form.getCategoryId(),8,"");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("obj", articles);
        return R.ok(map);
    }

    @RequestMapping(value = "queryOneArticles",method = RequestMethod.POST)
    @ApiOperation("根据文章ID返回文章数据对象")
    public R queryOneArticles(@RequestBody CmsForm form){
        Article article = CmsUtils.getArticle(form.getArticleId());
        article.setArticleData(articleDataService.get(article.getId()));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("obj", article);
        return R.ok(map);
    }

    @RequestMapping(value = "querySiteInfo",method = RequestMethod.POST)
    @ApiOperation("返回站点信息接口")
    public R querySiteInfo(@RequestBody CmsForm form){
        Site site = CmsUtils.getSite(form.getSiteId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("obj", site);
        return R.ok(map);
    }
}
