<% 
	/* content消息内容
		type  消息类型：info、success、warning、error、loading 
	*/
	if(isNotEmpty(content)){
		var ctype = "1";
		if(isNotEmpty(type)){
			if(type=="info"){
				ctype = "0";
			}else if(type=="success"){
				ctype = "1";
			}else if(type=="error"){
				ctype = "2";
			}else if(type=="loading"){
				ctype = "3";
			}else if(type=="warning"){
				ctype = "4";
			}
		}else{
			if(strutil.contain(content,"失败")){
				ctype = "2";
			}else{
				ctype = "1";
			}
		};
%>
	<script type="text/javascript">
        top.layer.msg("${content}", {icon: ${ctype}});
	</script>
	
<% } %>