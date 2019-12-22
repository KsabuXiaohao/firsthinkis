/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.sys.web;

import com.thinkis.common.persistence.Page;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.sys.entity.Log;
import com.thinkis.modules.sys.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志Controller
 * @author ThinkGem
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.findPage(new Page<Log>(request, response), log); 
        model.addAttribute("page", page);
		return "modules/sys/logList";
	}

	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = "listAjax",method=RequestMethod.GET)
	@ResponseBody
	public Result listAjax(Log log , HttpServletRequest request, HttpServletResponse response) {
		Page<Log> page = logService.findPage(new Page<Log>(request, response), log);
		return ResultGenerator.genSuccessResult(page.getCount(),page.getList());
	}
}
