/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.cms.web;

import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.service.SiteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 站点Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/site")
public class SiteController extends BaseController {

	@Autowired
	private SiteService siteService;
	
	@ModelAttribute
	public Site get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return siteService.get(id);
		}else{
			return new Site();
		}
	}
	
	@RequiresPermissions("cms:site:view")
	@RequestMapping(value = {"list", ""})
	public String list(Site site, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Site> page = siteService.findPage(new Page<Site>(request, response), site); 
        model.addAttribute("page", page);
		return "modules/cms/siteList";
	}

	@ResponseBody
	@RequiresPermissions("cms:site:view")
	@RequestMapping(value = {"listAjax"}, method=RequestMethod.GET)
	public Result listAjax(Site site, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Site> page = siteService.findPage(new Page<Site>(request, response), site);
		model.addAttribute("site", site);
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
	}

	@RequiresPermissions("cms:site:view")
	@RequestMapping(value = "form")
	public String form(Site site, Model model) {
		model.addAttribute("site", site);
		return "modules/cms/siteForm";
	}

	@RequiresPermissions("cms:site:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(Site site, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, site)){
			return ResultGenerator.genFailResult((String)model.asMap().get("message"));
		}
		siteService.save(site);
		return ResultGenerator.genSuccessResult("保存站点'" + site.getName() + "'成功");
	}

	@RequiresPermissions("cms:site:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(Site site, @RequestParam(required=false) Boolean isRe) {
		if (Site.isDefault(site.getId())){
			return ResultGenerator.genFailResult("删除站点失败, 不允许删除默认站点");
		}else{
			siteService.delete(site, isRe);
		}
		String message = (isRe!=null && isRe) ?"恢复":""+"删除站点成功";

		return ResultGenerator.genSuccessResult(message);
	}
	
	/**
	 * 选择站点
	 * @param id
	 * @return
	 */
	@RequiresPermissions("cms:site:select")
	@RequestMapping(value = "select")
	public String select(String id, boolean flag, HttpServletResponse response){
		if (id!=null){
			siteService.setDefaultSite(id);
		}
		if (flag){
			return "redirect:" + adminPath;
		}
		return "modules/cms/siteSelect";
	}
}
