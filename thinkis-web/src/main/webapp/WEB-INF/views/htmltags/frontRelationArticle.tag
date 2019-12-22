<dl class="fly-panel fly-list-one" >
	<dt class="fly-panel-title">${panelTitle}</dt>
	<%for(relation in relationList){%>
	<dd>
		<a href="${ctxFront}/view-${relation[0]}-${relation[1]}${@Global.getUrlSuffix()}">${@StringUtils.abbr(relation[2],30)}</a>
	</dd>
	<%}elsefor{%>
	<!-- 无数据时 -->
	<div class="fly-none">没有相关数据</div>
	<%}%>
</dl>