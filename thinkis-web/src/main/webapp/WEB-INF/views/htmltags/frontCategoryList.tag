<dl class="fly-panel fly-list-one">
	<dt class="fly-panel-title">${panelTitle}</dt>
	<%
		for(tpl in categoryList){
		    var url ="";
			if(isNotEmpty(tpl.href)){
			    if(strutil.index(tpl.href,'://')&&strutil.index(tpl.href,'mailto:')){
					url= ctxFront + tpl.href;
				}else{
			        url = tpl.href;
				}
			}else{
			    url = ctxFront+"/list-"+(tpl.id)+@Global.getUrlSuffix();
			}

	%>
	<dd>
		<a href="${url}" target="${tpl.target}">${tpl.name}</a>
	</dd>
	<%}elsefor{%>
	<!-- 无数据时 -->
    <div class="fly-none">没有相关数据</div>
	<%}%>
</dl>


