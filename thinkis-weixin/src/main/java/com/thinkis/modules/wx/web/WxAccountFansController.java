/**
 * Copyright &copy; 2017-2020 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.wx.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkis.modules.wx.entity.WxAccountFans;
import com.thinkis.modules.wx.service.Gzh1WxService;
import com.thinkis.modules.wx.service.WxAccountFansService;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;

import cn.hutool.core.lang.Console;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 公众号关注人Controller
 * @author liuzhiping
 * @version 2018-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/wxAccountFans")
public class WxAccountFansController extends BaseController {

	@Autowired
	private WxAccountFansService wxAccountFansService;
	
	@Autowired
 	private Gzh1WxService wxService;
	
	@ModelAttribute
	public WxAccountFans get(@RequestParam(required=false) String id) {
		WxAccountFans entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxAccountFansService.get(id);
		}
		if (entity == null){
			entity = new WxAccountFans();
		}
		return entity;
	}
	
	@RequiresPermissions("wx:wxAccountFans:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxAccountFans wxAccountFans, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxAccountFans> page = wxAccountFansService.findPage(new Page<WxAccountFans>(request, response), wxAccountFans); 
		model.addAttribute("page", page);
		return "modules/wx/wxAccountFansList";
	}

	@RequiresPermissions("wx:wxAccountFans:view")
	@RequestMapping(value = "listAjax",method=RequestMethod.GET)
	@ResponseBody
	public Result listAjax(WxAccountFans wxAccountFans,HttpServletRequest request, HttpServletResponse response) {
		Page<WxAccountFans> page = wxAccountFansService.findPage(new Page<WxAccountFans>(request, response), wxAccountFans);
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
}

	@RequiresPermissions("wx:wxAccountFans:view")
	@RequestMapping(value = "form")
	public String form(WxAccountFans wxAccountFans, Model model) {
		model.addAttribute("wxAccountFans", wxAccountFans);
		return "modules/wx/wxAccountFansForm";
	}
	
	@RequiresPermissions("wx:wxAccountFans:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(WxAccountFans wxAccountFans) {
		wxAccountFansService.save(wxAccountFans);
		return ResultGenerator.genSuccessResult();
	}
	
	@RequiresPermissions("wx:wxAccountFans:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(WxAccountFans wxAccountFans) {
		wxAccountFansService.delete(wxAccountFans);
		return ResultGenerator.genSuccessResult();
	}
	
	@RequiresPermissions("wx:wxAccountFans:edit")
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String selectedIds) {
		int success = wxAccountFansService.batchDelete(selectedIds);
		return ResultGenerator.genSuccessResult("成功删除"+success+"条数据");
	}

	@RequiresPermissions("wx:wxAccountFans:edit")
	@RequestMapping(value = "syncWxAccountFans")
	@ResponseBody
	public Result syncWxAccountFans() {
		List<WxMpUser> wxMpUserList =  wxAccountFansService.syncUserInfoList();
		return ResultGenerator.genSuccessResult("成功同步"+wxMpUserList.size()+"条数据");
	}

	@RequiresPermissions("wx:wxAccountFans:edit")
	@RequestMapping(value = "sendMessage")
	@ResponseBody
	public Result sendMessage(String openId) {
		WxMpKefuMessage wxMpKefuMessage = WxMpKefuMessage
		  .TEXT()
		  .toUser(openId)
		  .content("你好！")
		  .build();
		try {
			wxService.getKefuService().sendKefuMessage(wxMpKefuMessage);
		} catch (WxErrorException e) {
			Console.log("消息发送异常");
			e.printStackTrace();
		}
		return ResultGenerator.genSuccessResult("消息发送成功");
	}
	
}