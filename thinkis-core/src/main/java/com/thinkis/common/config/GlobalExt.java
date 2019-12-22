package com.thinkis.common.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

public class GlobalExt implements WebRenderExt {
	
	static long version = System.currentTimeMillis();
	
	@Override
    public void modify(Template template, GroupTemplate gt, HttpServletRequest request, HttpServletResponse response) {

	    //注册shiro函数包
        gt.registerFunctionPackage("shiro",new ShiroExt());

        //js,css 的版本编号
        template.binding("sysVersion",version);
        
        //后端应用根路径
        String ctx = request.getContextPath() + Global.getAdminPath();
        
        //前端应用根路径
        String ctxFront = request.getContextPath() + Global.getFrontPath();

        //静态文件根路径
        String ctxStatic = request.getContextPath()  + "/static";
        
        template.binding("ctx",ctx);
        template.binding("ctxFront",ctxFront);
        template.binding("ctxStatic",ctxStatic);
    }
}
