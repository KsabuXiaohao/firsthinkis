<div class="fly-panel fly-link">
    <div class="fly-panel-title">
        ${panelTitle!'友情链接'}
        <span class="fly-mid"></span>
        <a href="mailto:383516619@qq.com" class="fly-link fly-joinad">申请友链</a>
    </div>
    <dl class="fly-panel-main">
        <%
            var linkList = @CmsUtils.getLinkList(siteId, categoryId, isNotEmpty(pageSize)?pageSize:8, '');
            for(link in linkList){
        %>
        <dd><a href="${link.href}" target="_blank">${link.title}</a><dd>
        <%}elsefor{%>
        <dd class="fly-none">没有相关数据</dd>
        <%}%>
    </dl>
</div>