/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkis.common.config.Global;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.sys.entity.Office;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.service.OfficeService;
import com.thinkis.modules.sys.utils.DictUtils;
import com.thinkis.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
		try {
			List<Office> list = officeService.findList(office);
			model.addAttribute("list", list);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "modules/sys/officeList";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
//		User user = UserUtils.getUser();
//		if (office.getParent()==null || office.getParent().getId()==null){
//			office.setParent(user.getOffice());
//		}
//		office.setParent(officeService.get(office.getParent().getId()));
//		if (office.getArea()==null){
//			office.setArea(user.getOffice().getArea());
//		}
//		// 自动获取排序号
//		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
//			int size = 0;
//			List<Office> list = officeService.findAll();
//			for (int i=0; i<list.size(); i++){
//				Office e = list.get(i);
//				if (e.getParent()!=null && e.getParent().getId()!=null
//						&& e.getParent().getId().equals(office.getParent().getId())){
//					size++;
//				}
//			}
//			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
//		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(Office office, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (!beanValidator(model, office)) {
				return ResultGenerator.genFailResult((String) model.asMap().get("message"));
			}
			officeService.save(office);

			if (office.getChildDeptList() != null) {
				Office childOffice = null;
				for (String id : office.getChildDeptList()) {
					childOffice = new Office();
					childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
					childOffice.setParent(office);
					childOffice.setArea(office.getArea());
					childOffice.setType("2");
					childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade()) + 1));
					childOffice.setUseable(Global.YES);
					officeService.save(childOffice);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return ResultGenerator.genSuccessResult("保存机构" + office.getName() + "失败!,原因是："+e.getMessage()+"");
		}
		return ResultGenerator.genSuccessResult("保存机构" + office.getName() + "成功");
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(Office office, RedirectAttributes redirectAttributes) {
		officeService.delete(office);
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
}
