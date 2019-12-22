<li class="fly-panel fly-list-one">
	<h3 class="fly-panel-title">${panelTitle}</h3>
	<ul class="fly-case-list" style="width:100%;text-align: center;">
		<%
			var articleList = @CmsUtils.getArticleList(category.site.id, category.id, isNotEmpty(pageSize)?pageSize:8, 'posid:2, orderBy: \"hits desc\"');
			for(article in articleList){
		%>
		<li style="width:90%;margin: 0px;padding: 0px;">
			<a class="fly-case-img" href="${article.url}" target="_blank" >
				<img src="${article.image}" alt="${@StringUtils.abbr(article.title,40)}">
				<cite class="layui-btn layui-btn-primary layui-btn-small">去围观</cite>
			</a>
			<h2><a href="http://fly.layui.com/" target="_blank">${@StringUtils.abbr(article.title,40)}</a></h2>
			<p class="fly-case-desc">${article.description}</p>
		</li>
		<hr class="layui-bg-gray">
		<%}elsefor{%>
		<li class="fly-none">没有相关数据</li>
		<%}%>
	</ul>
</li>