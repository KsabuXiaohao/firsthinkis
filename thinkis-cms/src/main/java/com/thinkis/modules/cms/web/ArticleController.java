/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkis.modules.cms.entity.Article;
import com.thinkis.modules.cms.entity.Category;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.service.ArticleDataService;
import com.thinkis.modules.cms.service.ArticleService;
import com.thinkis.modules.cms.service.CategoryService;
import com.thinkis.modules.cms.service.FileTplService;
import com.thinkis.modules.cms.service.SiteService;
import com.thinkis.modules.cms.utils.CmsUtils;
import com.thinkis.modules.cms.utils.TplUtils;
import com.thinkis.common.mapper.JsonMapper;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.modules.sys.utils.UserUtils;

/**
 * 文章Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
	@Autowired
	private CategoryService categoryService;
    @Autowired
   	private FileTplService fileTplService;
    @Autowired
   	private SiteService siteService;
	
	@ModelAttribute
	public Article get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return articleService.get(id);
		}else{
			return new Article();
		}
	}
	
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = {"list", ""})
	public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
        model.addAttribute("page", page);
		return "modules/cms/articleList";
	}

	@ResponseBody
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = {"listAjax"}, method=RequestMethod.GET)
	public Result listAjax(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		String categoryId = request.getParameter("categoryId");
		if (StringUtils.isNotEmpty(categoryId)) {
			Category category = new Category();
			category.setId(categoryId);
			article.setCategory(category);
		}
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
	}

	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "form")
	public String form(Article article, Model model) {
		// 如果当前传参有子节点，则选择取消传参选择
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId())){
			List<Category> list = categoryService.findByParentId(article.getCategory().getId(), Site.getCurrentSiteId());
			if (list.size() > 0){
				article.setCategory(null);
			}else{
				article.setCategory(categoryService.get(article.getCategory().getId()));
			}
		}
		article.setArticleData(articleDataService.get(article.getId()));
//		if (article.getCategory()=null && StringUtils.isNotBlank(article.getCategory().getId())){
//			Category category = categoryService.get(article.getCategory().getId());
//		}
        model.addAttribute("contentViewList",getTplContent());
        model.addAttribute("article_DEFAULT_TEMPLATE",Article.DEFAULT_TEMPLATE);
		model.addAttribute("article", article);
		CmsUtils.addViewConfigAttribute(model, article.getCategory());
		return "modules/cms/articleForm";
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(Article article, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, article)){
			return ResultGenerator.genFailResult((String)model.asMap().get("message"));
		}
		articleService.save(article);
		return ResultGenerator.genSuccessResult("保存文章'" + StringUtils.abbr(article.getTitle(),50) + "'成功");
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(Article article,@RequestParam(required=false) Boolean isRe) {
		// 如果没有审核权限，则不允许删除或发布。
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")){
			return ResultGenerator.genFailResult("你没有删除或发布权限");
		}
		articleService.delete(article, isRe);
		return ResultGenerator.genSuccessResult((isRe!=null&&isRe?"发布":"删除")+"文章成功");
	}

	/**
	 * 文章选择列表
	 */
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "selectList")
	public String selectList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(article, request, response, model);
		return "modules/cms/articleSelectList";
	}
	
	/**
	 * 通过编号获取文章标题
	 */
	@RequiresPermissions("cms:article:view")
	@ResponseBody
	@RequestMapping(value = "findByIds")
	public String findByIds(String ids) {
		List<Object[]> list = articleService.findByIds(ids);
		return JsonMapper.nonDefaultMapper().toJson(list);
	}

    private List<String> getTplContent() {
   		List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
   		tplList = TplUtils.tplTrim(tplList, Article.DEFAULT_TEMPLATE, "");
   		return tplList;
   	}
}
