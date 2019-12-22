package com.thinkis.modules.cms.tags;

import cn.hutool.core.convert.Convert;
import com.thinkis.common.mapper.JsonMapper;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.SpringContextHolder;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.modules.cms.entity.Category;
import com.thinkis.modules.cms.entity.Link;
import com.thinkis.modules.cms.entity.Site;
import com.thinkis.modules.cms.service.LinkService;
import org.beetl.core.GeneralVarTagBinding;

import java.util.Map;

/**
 * 获取链接列表
 * @param siteId 站点编号
 * @param categoryId 分类编号
 * @param number 获取数目
 * @param param  预留参数，例： key1:'value1', key2:'value2' ...
 * @return
 */
public class LinksTag extends GeneralVarTagBinding {

    private static LinkService linkService = SpringContextHolder.getBean(LinkService.class);

    @Override
    public void render(){

        String siteId = (String)this.getAttributeValue("siteId");
        String categoryId = (String)this.getAttributeValue("categoryId");
        int count = Convert.toInt(this.getAttributeValue("count"));
        String param = (String)this.getAttributeValue("param");


        Page<Link> page = new Page<Link>(1, count, -1);
        Link link = new Link(new Category(categoryId, new Site(siteId)));
        if (StringUtils.isNotBlank(param)){
            @SuppressWarnings({ "unused", "rawtypes" })
            Map map = JsonMapper.getInstance().fromJson("{"+param+"}", Map.class);
        }
        link.setDelFlag(Link.DEL_FLAG_NORMAL);
        page = linkService.findPage(page, link, false);

        for(Link obj : page.getList()){
            this.binds(obj);
            this.doBodyRender();
        }
    }
}
