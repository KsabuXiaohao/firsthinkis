<fieldset class="layui-elem-field layui-field-title">
	<legend>流转信息</legend>
</fieldset>
<div id="histoicFlowList">
	正在加载流转信息...
</div>
<script type="text/javascript">
	$.get("${ctx}/act/task/histoicFlow?procInsId=${procInsId!}&startAct=${startAct!}&endAct=${endAct!}&t="+new Date().getTime(), function(data){
		$("#histoicFlowList").html(data);
	});
</script>