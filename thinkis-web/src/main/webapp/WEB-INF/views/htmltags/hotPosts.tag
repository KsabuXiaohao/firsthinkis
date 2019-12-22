<dl class="fly-panel fly-list-one">
    <dt class="fly-panel-title">本周热议</dt>
    <%
        var comments = @CmsUtils.getHotsArticles(siteId,5);
        for(var comment in comments){
    %>
    <dd>
        <a href="${ctxFront}/view-${comment.category.id}-${comment.contentId}${@Global.getUrlSuffix()}">${comment.title}</a>
        <span><i class="iconfont icon-pinglun1"></i>${comment.totalCount}</span>
    </dd>
    <%}elsefor{%>
    <div class="fly-none">没有相关数据</div>
    <%}%>
</dl>