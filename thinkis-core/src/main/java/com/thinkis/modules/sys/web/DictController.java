/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.sys.web;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkis.common.config.Global;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.modules.sys.entity.Dict;
import com.thinkis.modules.sys.service.DictService;

/**
 * 字典Controller
 * @author ThinkGem
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	
	@ModelAttribute
	public Dict get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return dictService.get(id);
		}else{
			return new Dict();
		}
	}
	
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"list", ""})
	public String list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<String> typeList = dictService.findTypeList();
		model.addAttribute("typeList", typeList);
        Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict); 
        model.addAttribute("page", page);
		return "modules/sys/dictList";
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "listAjax",method=RequestMethod.GET)
	@ResponseBody
	public Result listAjax(Dict dict, HttpServletRequest request, HttpServletResponse response) {
		Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict);
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
	}

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = "form")
	public String form(Dict dict, Model model) {
		model.addAttribute("dict", dict);
		return "modules/sys/dictForm";
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save")//@Valid 
	@ResponseBody
	public Result save(Dict dict, Model model) {
		if (!beanValidator(model, dict)){
			return ResultGenerator.genFailResult((String)model.asMap().get("message"));
		}
		dictService.save(dict);
		return ResultGenerator.genSuccessResult("保存字典'" + dict.getLabel() + "'成功");
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(Dict dict) {
		dictService.delete(dict);
		return ResultGenerator.genSuccessResult("删除字典成功");
	}

	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String selectedIds) {
		if(Global.isDemoMode()){
			return  ResultGenerator.genFailResult("演示模式，不允许操作！");
		}
		int success = dictService.batchDelete(selectedIds);
		return ResultGenerator.genSuccessResult("成功删除"+success+"条数据");
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Dict dict = new Dict();
		dict.setType(type);
		List<Dict> list = dictService.findList(dict);
		for (int i=0; i<list.size(); i++){
			Dict e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", StringUtils.replace(e.getLabel(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	
	@ResponseBody
	@RequestMapping(value = "listData")
	public List<Dict> listData(@RequestParam(required=false) String type) {
		Dict dict = new Dict();
		dict.setType(type);
		return dictService.findList(dict);
	}

}
