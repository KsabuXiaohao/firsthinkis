/**
 * Copyright &copy; 2017-2020 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.oss.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.oss.entity.Oss;
import com.thinkis.modules.oss.service.OssService;

/**
 * 附件管理Controller
 * @author liuzhiping
 * @version 2018-05-09
 */
@Controller
@RequestMapping(value = "${adminPath}/oss/oss")
public class OssController extends BaseController {
	
	@Autowired
	private OssService ossService;
	
	@ModelAttribute
	public Oss get(@RequestParam(required=false) String id) {
		Oss entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ossService.get(id);
		}
		if (entity == null){
			entity = new Oss();
		}
		return entity;
	}
	
	@RequiresPermissions("oss:oss:view")
	@RequestMapping(value = {"list", ""})
	public String list(Oss oss, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Oss> page = ossService.findPage(new Page<Oss>(request, response), oss); 
		model.addAttribute("page", page);
		return "modules/oss/ossList";
	}

	@RequiresPermissions("oss:oss:view")
	@RequestMapping(value = "listAjax",method=RequestMethod.GET)
	@ResponseBody
	public Result listAjax(Oss oss,HttpServletRequest request, HttpServletResponse response) {
		Page<Oss> page = ossService.findPage(new Page<Oss>(request, response), oss); 
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
	}

	@RequiresPermissions("oss:oss:view")
	@RequestMapping(value = "form")
	public String form(Oss oss, Model model) {
		model.addAttribute("oss", oss);
		return "modules/oss/ossForm";
	}
	
	@RequiresPermissions("oss:oss:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(Oss oss) {
		Oss obj = ossService.get(oss);
		obj.setStatus(oss.getStatus());
		obj.setFileName(oss.getFileName());
		obj.setRemarks(oss.getRemarks());
		ossService.save(obj);
		return ResultGenerator.genSuccessResult();
	}
	
	@RequiresPermissions("oss:oss:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(Oss oss) {
		ossService.delete(oss);
		return ResultGenerator.genSuccessResult();
	}
	
	@RequiresPermissions("oss:oss:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String selectedIds) {
		int success = ossService.batchDelete(selectedIds);
		return ResultGenerator.genSuccessResult("成功删除"+success+"条数据");
	}

}