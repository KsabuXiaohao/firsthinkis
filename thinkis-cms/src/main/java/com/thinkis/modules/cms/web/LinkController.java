/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.web;

import com.thinkis.common.mapper.JsonMapper;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.cms.entity.Category;
import com.thinkis.modules.cms.entity.Link;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.service.CategoryService;
import com.thinkis.modules.cms.service.LinkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 链接Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/link")
public class LinkController extends BaseController {

	@Autowired
	private LinkService linkService;
	@Autowired
	private CategoryService categoryService;
	
	@ModelAttribute
	public Link get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return linkService.get(id);
		}else{
			return new Link();
		}
	}
	
	@RequiresPermissions("cms:link:view")
	@RequestMapping(value = {"list", ""})
	public String list(Link link, HttpServletRequest request, HttpServletResponse response, Model model) {
//		User user = UserUtils.getUser();
//		if (!user.isAdmin() && !SecurityUtils.getSubject().isPermitted("cms:link:audit")){
//			link.setUser(user);
//		}
        Page<Link> page = linkService.findPage(new Page<Link>(request, response), link, true); 
        model.addAttribute("page", page);
		return "modules/cms/linkList";
	}

	@ResponseBody
	@RequiresPermissions("cms:link:view")
	@RequestMapping(value = {"listAjax"}, method=RequestMethod.GET)
	public Result listAjax(Link link,  HttpServletRequest request, HttpServletResponse response, Model model) {
		String categoryId = request.getParameter("categoryId");
		if (StringUtils.isNotEmpty(categoryId)) {
			Category category = new Category();
			category.setId(categoryId);
			link.setCategory(category);
		}
		Page<Link> page = linkService.findPage(new Page<Link>(request, response), link, true);
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
	}

	@RequiresPermissions("cms:link:view")
	@RequestMapping(value = "form")
	public String form(Link link, Model model) {
		// 如果当前传参有子节点，则选择取消传参选择
		if (link.getCategory()!=null && StringUtils.isNotBlank(link.getCategory().getId())){
			List<Category> list = categoryService.findByParentId(link.getCategory().getId(), Site.getCurrentSiteId());
			if (list.size() > 0){
				link.setCategory(null);
			}else{
				link.setCategory(categoryService.get(link.getCategory().getId()));
			}
		}
		model.addAttribute("link", link);
		return "modules/cms/linkForm";
	}

	@RequiresPermissions("cms:link:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(Link link, Model model) {
		if (!beanValidator(model, link)){
			return ResultGenerator.genFailResult((String)model.asMap().get("message"));
		}
		linkService.save(link);
		return ResultGenerator.genSuccessResult("保存链接'" + StringUtils.abbr(link.getTitle(),50) + "'成功");
	}

	@RequiresPermissions("cms:link:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result Result(Link link, String categoryId, @RequestParam(required=false) Boolean isRe, RedirectAttributes redirectAttributes) {
		linkService.delete(link, isRe);
		return ResultGenerator.genSuccessResult((isRe!=null&&isRe?"发布":"删除")+"链接成功");
	}

	/**
	 * 链接选择列表
	 */
	@RequiresPermissions("cms:link:view")
	@RequestMapping(value = "selectList")
	public String selectList(Link link, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(link, request, response, model);
		return "modules/cms/linkSelectList";
	}
	
	/**
	 * 通过编号获取链接名称
	 */
	@RequiresPermissions("cms:link:view")
	@ResponseBody
	@RequestMapping(value = "findByIds")
	public String findByIds(String ids) {
		List<Object[]> list = linkService.findByIds(ids);
		return JsonMapper.nonDefaultMapper().toJson(list);
	}
}
