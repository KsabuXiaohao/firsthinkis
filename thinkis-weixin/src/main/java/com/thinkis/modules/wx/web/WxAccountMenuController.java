/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.wx.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkis.modules.wx.entity.WxAccountMenu;
import com.thinkis.modules.wx.service.WxAccountMenuService;
import com.thinkis.common.config.Global;
import com.thinkis.common.web.BaseController;

/**
 * 公众号菜单Controller
 * @author liuzhiping
 * @version 2018-04-15
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/wxAccountMenu")
public class WxAccountMenuController extends BaseController {

	@Autowired
	private WxAccountMenuService wxAccountMenuService;
	
	@ModelAttribute
	public WxAccountMenu get(@RequestParam(required=false) String id) {
		WxAccountMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxAccountMenuService.get(id);
		}
		if (entity == null){
			entity = new WxAccountMenu();
		}
		return entity;
	}
	
	@RequiresPermissions("wx:wxAccountMenu:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxAccountMenu wxAccountMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<WxAccountMenu> list = wxAccountMenuService.findList(wxAccountMenu); 
		model.addAttribute("list", list);
		return "modules/wx/wxAccountMenuList";
	}

	@RequiresPermissions("wx:wxAccountMenu:view")
	@RequestMapping(value = "form")
	public String form(WxAccountMenu wxAccountMenu, Model model) {
		if (wxAccountMenu.getParent()!=null && StringUtils.isNotBlank(wxAccountMenu.getParent().getId())){
			wxAccountMenu.setParent(wxAccountMenuService.get(wxAccountMenu.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(wxAccountMenu.getId())){
				WxAccountMenu wxAccountMenuChild = new WxAccountMenu();
				wxAccountMenuChild.setParent(new WxAccountMenu(wxAccountMenu.getParent().getId()));
				List<WxAccountMenu> list = wxAccountMenuService.findList(wxAccountMenu); 
				if (list.size() > 0){
					wxAccountMenu.setSort(list.get(list.size()-1).getSort());
					if (wxAccountMenu.getSort() != null){
						wxAccountMenu.setSort(wxAccountMenu.getSort() + 30);
					}
				}
			}
		}
		if (wxAccountMenu.getSort() == null){
			wxAccountMenu.setSort(30);
		}
		model.addAttribute("wxAccountMenu", wxAccountMenu);
		return "modules/wx/wxAccountMenuForm";
	}

	@RequiresPermissions("wx:wxAccountMenu:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(WxAccountMenu wxAccountMenu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxAccountMenu)){
			return ResultGenerator.genFailResult((String)model.asMap().get("message"));
		}
		wxAccountMenuService.save(wxAccountMenu);
		return ResultGenerator.genSuccessResult("保存公众号菜单成功");
	}

	@RequiresPermissions("wx:wxAccountMenu:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(WxAccountMenu wxAccountMenu, RedirectAttributes redirectAttributes) {
		wxAccountMenuService.delete(wxAccountMenu);
		return ResultGenerator.genSuccessResult();
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<WxAccountMenu> list = wxAccountMenuService.findList(new WxAccountMenu());
		for (int i=0; i<list.size(); i++){
			WxAccountMenu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 生成微信菜单
	 *
	 * @param id groupId
	 * @param model xx
	 * @return xx
	 */
	@RequiresPermissions("wx:wxAccountMenu:edit")
	@RequestMapping(value = "/createMenu/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String createMenu(@PathVariable("id") String id, Model model) {
		wxAccountMenuService.createMenu(id);
		return "success";
	}
}