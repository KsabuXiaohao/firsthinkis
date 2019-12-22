/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.cms.entity.Article;
import com.thinkis.modules.cms.entity.Category;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.service.CategoryService;
import com.thinkis.modules.cms.service.FileTplService;
import com.thinkis.modules.cms.service.SiteService;
import com.thinkis.modules.cms.utils.TplUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 栏目Controller
 * @author ThinkGem
 * @version 2013-4-21
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
    @Autowired
   	private FileTplService fileTplService;
    @Autowired
   	private SiteService siteService;
	
	@ModelAttribute("category")
	public Category get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return categoryService.get(id);
		}else{
			return new Category();
		}
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Category> list = Lists.newArrayList();
		List<Category> sourcelist = categoryService.findByUser(true, null);
		Category.sortList(list, sourcelist, "1");
        model.addAttribute("list", list);
		return "modules/cms/categoryList";
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		if (category == null || category.getParent()==null||category.getParent().getId()==null){
			category.setParent(new Category("1"));
		}
		Category parent = categoryService.get(category.getParent().getId());
		category.setParent(parent);
		if (category.getOffice()==null||category.getOffice().getId()==null){
			category.setOffice(parent.getOffice());
		}
        model.addAttribute("listViewList",getTplContent(Category.DEFAULT_TEMPLATE));
        model.addAttribute("category_DEFAULT_TEMPLATE",Category.DEFAULT_TEMPLATE);
        model.addAttribute("contentViewList",getTplContent(Article.DEFAULT_TEMPLATE));
        model.addAttribute("article_DEFAULT_TEMPLATE",Article.DEFAULT_TEMPLATE);
		model.addAttribute("office", category.getOffice());
		model.addAttribute("category", category);
		return "modules/cms/categoryForm";
	}

	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(Category category, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, category)){
			return ResultGenerator.genFailResult((String)model.asMap().get("message"));
		}
		categoryService.save(category);
		return ResultGenerator.genSuccessResult("保存栏目'" + category.getName() + "'成功");
	}

	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(Category category, RedirectAttributes redirectAttributes) {
		if (Category.isRoot(category.getId())){
			return ResultGenerator.genFailResult("删除栏目失败, 不允许删除顶级栏目或编号为空");
		}else{
			categoryService.delete(category);
		}
		return ResultGenerator.genSuccessResult( "删除栏目成功");
	}

	/**
	 * 批量修改栏目排序
	 */
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	int len = ids.length;
    	Category[] entitys = new Category[len];
    	for (int i = 0; i < len; i++) {
    		entitys[i] = categoryService.get(ids[i]);
    		entitys[i].setSort(sorts[i]);
    		categoryService.save(entitys[i]);
    	}
    	addMessage(redirectAttributes, "保存栏目排序成功!");
		return "redirect:" + adminPath + "/cms/category/";
	}
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String module, @RequestParam(required=false) String extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Category> list = categoryService.findByUser(true, module);
		for (int i=0; i<list.size(); i++){
			Category e = list.get(i);
			if (extId == null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				map.put("module", e.getModule());
				mapList.add(map);
			}
		}
		return mapList;
	}

    private List<String> getTplContent(String prefix) {
   		List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
   		tplList = TplUtils.tplTrim(tplList, prefix, "");
   		return tplList;
   	}
}
