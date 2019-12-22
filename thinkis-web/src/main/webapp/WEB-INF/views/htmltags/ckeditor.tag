<script type="text/javascript">include('ckeditor_lib','${ctxStatic}/ckeditor/',['ckeditor.js']);</script>
<script type="text/javascript">
	var ${replace}Ckeditor = CKEDITOR.replace("${replace!}");
	${replace}Ckeditor.config.height = "${height!}";
    <%if(isNotEmpty(uploadPath)){%>
	${replace}Ckeditor.config.ckfinderPath="${ctxStatic}/ckfinder";
	var date = new Date(), year = date.getFullYear(), month = (date.getMonth()+1)>9?date.getMonth()+1:"0"+(date.getMonth()+1);
	${replace}Ckeditor.config.ckfinderUploadPath="${uploadPath!}/"+year+"/"+month+"/";
	<%}%>
</script>