/**
 * Copyright &copy; 2017-2020 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.sys.web;

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
import com.thinkis.modules.sys.entity.OpenOauth;
import com.thinkis.modules.sys.service.OpenOauthService;

/**
 * 认证信息管理Controller
 * @author liuzhiping
 * @version 2018-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/openOauth")
public class OpenOauthController extends BaseController {

	@Autowired
	private OpenOauthService openOauthService;
	
	@ModelAttribute
	public OpenOauth get(@RequestParam(required=false) String id) {
		OpenOauth entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = openOauthService.get(id);
		}
		if (entity == null){
			entity = new OpenOauth();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:openOauth:view")
	@RequestMapping(value = {"list", ""})
	public String list(OpenOauth openOauth, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OpenOauth> page = openOauthService.findPage(new Page<OpenOauth>(request, response), openOauth); 
		model.addAttribute("page", page);
		return "modules/sys/openOauthList";
	}

	@RequiresPermissions("sys:openOauth:view")
	@RequestMapping(value = "listAjax",method=RequestMethod.GET)
	@ResponseBody
	public Result listAjax(OpenOauth openOauth,HttpServletRequest request, HttpServletResponse response) {
		Page<OpenOauth> page = openOauthService.findPage(new Page<OpenOauth>(request, response), openOauth); 
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
	}

	@RequiresPermissions("sys:openOauth:view")
	@RequestMapping(value = "form")
	public String form(OpenOauth openOauth, Model model) {
		model.addAttribute("openOauth", openOauth);
		return "modules/sys/openOauthForm";
	}
	
	@RequiresPermissions("sys:openOauth:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(OpenOauth openOauth) {
		openOauthService.save(openOauth);
		return ResultGenerator.genSuccessResult();
	}
	
	@RequiresPermissions("sys:openOauth:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(OpenOauth openOauth) {
		openOauthService.delete(openOauth);
		return ResultGenerator.genSuccessResult();
	}
	
	@RequiresPermissions("sys:openOauth:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String selectedIds) {
		int success = openOauthService.batchDelete(selectedIds);
		return ResultGenerator.genSuccessResult("成功删除"+success+"条数据");
	}

}