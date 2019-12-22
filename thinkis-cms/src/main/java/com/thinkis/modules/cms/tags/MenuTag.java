package com.thinkis.modules.cms.tags;

import com.thinkis.common.config.Global;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.CacheUtils;
import com.thinkis.common.utils.SpringContextHolder;
import com.thinkis.modules.cms.entity.Category;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.service.CategoryService;
import org.beetl.core.GeneralVarTagBinding;

import java.util.List;

public class MenuTag extends GeneralVarTagBinding {

    private static CategoryService categoryService = SpringContextHolder.getBean(CategoryService.class);
    private static final String CMS_CACHE = "cmsCache";

    @Override
    public void render(){

        String siteId = (String)this.getAttributeValue("siteId");
        List<Category> mainNavList = (List<Category>)CacheUtils.get(CMS_CACHE, "mainNavList_"+siteId);
        if (null==mainNavList || mainNavList.isEmpty()){
            Category category = new Category();
            category.setSite(new Site(siteId));
            category.setParent(new Category("1"));
            category.setInMenu(Global.SHOW);
            Page<Category> page = new Page<Category>(1, -1);
            page = categoryService.find(page, category);
            mainNavList = page.getList();
            CacheUtils.put(CMS_CACHE, "mainNavList_"+siteId, mainNavList);
        }

        for(Category menu : mainNavList){
            this.binds(menu);
            this.doBodyRender();
        }
    }
}
