/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.sys.web;

import java.util.Date;

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

import com.thinkis.common.config.Global;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.modules.sys.entity.Sequence;
import com.thinkis.modules.sys.service.SequenceService;

/**
 * 序列键管理Controller
 * @author liuzhiping
 * @version 2017-08-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sequence")
public class SequenceController extends BaseController {

	@Autowired
	private SequenceService sequenceService;
	
	@ModelAttribute
	public Sequence get(@RequestParam(required=false) String id) {
		Sequence entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sequenceService.get(id);
		}
		if (entity == null){
			entity = new Sequence();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:sequence:view")
	@RequestMapping(value = {"list", ""})
	public String list(Sequence sequence, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Sequence> page = sequenceService.findPage(new Page<Sequence>(request, response), sequence); 
		model.addAttribute("page", page);
		return "modules/sys/sequenceList";
	}

	@RequiresPermissions("sys:sequence:view")
	@RequestMapping(value = "listAjax",method=RequestMethod.GET)
	@ResponseBody
	public Result listAjax(Sequence sequence,HttpServletRequest request, HttpServletResponse response) {
		Page<Sequence> page = sequenceService.findPage(new Page<Sequence>(request, response), sequence);
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
	}

	@RequiresPermissions("sys:sequence:view")
	@RequestMapping(value = "form")
	public String form(Sequence sequence, Model model) {
		model.addAttribute("sequence", sequence);
		return "modules/sys/sequenceForm";
	}

	@RequiresPermissions("sys:sequence:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(Sequence sequence, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sequence)){
			return ResultGenerator.genFailResult((String)model.asMap().get("message"));
		}
		sequence.setKeyDate(new Date());
		sequenceService.save(sequence);
		return ResultGenerator.genSuccessResult("保存序列键成功");
	}

	@RequiresPermissions("sys:sequence:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(Sequence sequence, RedirectAttributes redirectAttributes) {
		sequenceService.delete(sequence);
		return ResultGenerator.genSuccessResult();
	}

	@RequiresPermissions("sys:sequence:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String selectedIds) {
		int success = sequenceService.batchDelete(selectedIds);
		return ResultGenerator.genSuccessResult("成功删除"+success+"条数据");
	}

}