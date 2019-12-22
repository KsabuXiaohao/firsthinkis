<ol id="${input}Preview"></ol>
<% if(has(readonly)||readonly!="true"){%>
<a href="javascript:" onclick="${input}FinderOpen();" class="layui-btn layui-btn-xs">${selectMultiple=="true"?'添加':'选择'}</a>
&nbsp;<a href="javascript:" onclick="${input}DelAll();" class="layui-btn layui-btn-xs">清除</a>
<%}%>
<script type="text/javascript">
	layui.use(['form'],function(){
		var form = layui.form;
	});
	function ${input}FinderOpen(){
        <%
            var ctype = type;
            if(type=="thumb"){
                ctype = "images";
            }
        %>
		var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);
		var url = "${ctxStatic}/ckfinder/ckfinder.html?type=${ctype}&start=${ctype}:${uploadPath}/"+year+"/"+month+
			"/&action=js&func=${input}SelectAction&thumbFunc=${input}ThumbSelectAction&cb=${input}Callback&dts=${type == 'thumb'?'1':'0'}&sm=${selectMultiple=="true"?1:0}";
		windowOpen(url,"文件管理",900,600);
		//top.$.jBox("iframe:"+url+"&pwMf=1", {title: "文件管理", width: 1000, height: 500, buttons:{'关闭': true}});
	}
	function ${input}SelectAction(fileUrl, data, allFiles){
		var url="", files=ckfinderAPI.getSelectedFiles();
		for(var i=0; i<files.length; i++){
            <%if(type=="thumb"){%>
			url += files[i].getThumbnailUrl();
			<%}else{%>
			url += files[i].getUrl();
			<%}%>
			if (i<files.length-1) url+="|";
		}
        <%if(selectMultiple=="true"){%>
		$("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:"|"+url));
        <%}else{%>
		$("#${input}").val(url);
        <%}%>
		${input}Preview();
	}
	function ${input}ThumbSelectAction(fileUrl, data, allFiles){
		var url="", files=ckfinderAPI.getSelectedFiles();
		for(var i=0; i<files.length; i++){
			url += files[i].getThumbnailUrl();
			if (i<files.length-1) url+="|";
		}//<c:if test="${selectMultiple}">
        <%if(selectMultiple=="true"){%>
		$("#${input}").val($("#${input}").val()+($("#${input}").val(url)==""?url:"|"+url));
        <%}else{%>
		$("#${input}").val(url);
        <%}%>
		${input}Preview();
	}
	function ${input}Callback(api){
		ckfinderAPI = api;
	}
	function ${input}Del(obj){
		var url = $(obj).prev().attr("url");
		$("#${input}").val($("#${input}").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
		${input}Preview();
	}
	function ${input}DelAll(){
		$("#${input}").val("");
		${input}Preview();
	}
	function ${input}Preview(){
		var li, urls = $("#${input}").val().split("|");
		$("#${input}Preview").children().remove();
		for (var i=0; i<urls.length; i++){
			if (urls[i]!=""){
                <%if(type=="thumb"||type=="images"){%>
				li = "<li><img src=\""+urls[i]+"\" url=\""+urls[i]+"\" style=\"max-width:${isEmpty(maxWidth) ? 200 : maxWidth}px;max-height:${isEmpty(maxHeight) ? 200 : maxHeight}px;_height:${isEmpty(maxHeight) ? 200 : maxHeight}px;border:0;padding:3px;\">";
				<%}else{%>
				li = "<li><a href=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";
                <%}%>
				li += "&nbsp;&nbsp;<% if(readonly!="true"){%><a href=\"javascript:\" onclick=\"${input}Del(this);\">×</a><%}%></li>";
				$("#${input}Preview").append(li);
			}
		}
		if ($("#${input}Preview").text() == ""){
			$("#${input}Preview").html("<li style='list-style:none;padding-top:5px;'>无</li>");
		}
	}
	${input}Preview();
</script>