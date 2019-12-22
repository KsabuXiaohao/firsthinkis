/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkis.common.config.Global;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.modules.sys.entity.Menu;
import com.thinkis.modules.sys.service.SystemService;
import com.thinkis.modules.sys.utils.UserUtils;

/**
 * 菜单Controller
 *
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private SystemService systemService;

    @ModelAttribute("menu")
    public Menu get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return systemService.getMenu(id);
        } else {
            return new Menu();
        }
    }

    @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<Menu> list = Lists.newArrayList();
        List<Menu> sourcelist = systemService.findAllMenu();
        Menu.sortList(list, sourcelist, Menu.getRootId(), true);
        model.addAttribute("list", list);
        return "modules/sys/menuList";
    }

    @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = "form")
    public String form(Menu menu, Model model) {
        if (menu.getParent() == null || menu.getParent().getId() == null) {
            menu.setParent(new Menu(Menu.getRootId()));
        }
        menu.setParent(systemService.getMenu(menu.getParent().getId()));
        // 获取排序号，最末节点排序号+30
        if (StringUtils.isBlank(menu.getId())) {
            List<Menu> list = Lists.newArrayList();
            List<Menu> sourcelist = systemService.findAllMenu();
            Menu.sortList(list, sourcelist, menu.getParentId(), false);
            if (list.size() > 0) {
                menu.setSort(list.get(list.size() - 1).getSort() + 30);
            }
        }
        model.addAttribute("menu", menu);
        return "modules/sys/menuForm";
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "save")
    @ResponseBody
    public Result save(Menu menu, Model model, RedirectAttributes redirectAttributes) {
        if (!UserUtils.getUser().isAdmin()) {
            return ResultGenerator.genFailResult("越权操作，只有超级管理员才能添加或修改数据！");
        }
        if (!beanValidator(model, menu)) {
            return ResultGenerator.genFailResult((String) model.asMap().get("message"));
        }
        systemService.saveMenu(menu);
        return ResultGenerator.genSuccessResult("保存菜单'" + menu.getName() + "'成功");
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "delete")
    public String delete(Menu menu, RedirectAttributes redirectAttributes) {
//		if (Menu.isRoot(id)){
//			addMessage(redirectAttributes, "删除菜单失败, 不允许删除顶级菜单或编号为空");
//		}else{
        systemService.deleteMenu(menu);
        addMessage(redirectAttributes, "删除菜单成功");
//		}
        return "redirect:" + adminPath + "/sys/menu/";
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "tree")
    public String tree() {
        return "modules/sys/menuTree";
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "treeselect")
    public String treeselect(String parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "modules/sys/menuTreeselect";
    }

    /**
     * 批量修改菜单排序
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "updateSort")
    public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
        for (int i = 0; i < ids.length; i++) {
            Menu menu = new Menu(ids[i]);
            menu.setSort(sorts[i]);
            systemService.updateMenuSort(menu);
        }
        addMessage(redirectAttributes, "保存菜单排序成功!");
        return "redirect:" + adminPath + "/sys/menu/";
    }

    /**
     * isShowHide是否显示隐藏菜单
     *
     * @param extId
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Menu> list = systemService.findAllMenu();
        for (int i = 0; i < list.size(); i++) {
            Menu e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                if (isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")) {
                    continue;
                }
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
     * 获取一级菜单
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getFirstMenuList", method = RequestMethod.GET)
    public List<Map<String, Object>> getFirstMenuList() {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Menu> list = UserUtils.getFirstMenuList();
        for (Menu menu : list) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("title", menu.getName());
            map.put("icon", menu.getIcon());
            map.put("id", menu.getId());
            map.put("url", menu.getHref());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 根据一级菜单id获取子菜单
     *
     * @param menuId
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "getMenuData", method = RequestMethod.GET)
    public List<Map<String, Object>> getMenuData(@RequestParam(required = true) String menuId) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Menu> list = systemService.findAllMenu();
        for (Menu menu : list) {
            if (menu.getParent().getId().equals(menuId) && menu.getIsShow().equals("1")) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", menu.getId());
                map.put("title", menu.getName());
                map.put("icon", menu.getIcon());
                map.put("url", menu.getHref());
                map.put("spread", false);
                List<Map<String, Object>> mapList2 = Lists.newArrayList();
                for (Menu menu2 : list) {
                    if (menu2.getParent().getId().equals(menu.getId()) && menu2.getIsShow().equals("1")) {
                        Map<String, Object> map2 = Maps.newHashMap();
                        map2.put("id", menu2.getId());
                        map2.put("title", menu2.getName());
                        map2.put("icon", menu2.getIcon());
                        map2.put("url", menu2.getHref());
                        map2.put("spread", false);
                        mapList2.add(map2);
                    }
                }
                map.put("children", mapList2);
                mapList.add(map);
            }
        }
        return mapList;
    }
}
