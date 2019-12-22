<i id="${id}Icon" class="fa ${isNotEmpty(value)?value:' hide'}"></i>&nbsp;
<label id="${id}IconLabel">${isNotEmpty(value)?value:'无'}</label>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value}"/>
<a id="${id}Button" href="javascript:" class="layui-btn btn-default layui-btn-sm">选择</a>&nbsp;&nbsp;
<script type="text/javascript">
	$("#${id}Button").click(function(){
		layui.layer.open({
			  type: 2,
			  title: "选择图标",
			  shadeClose: true,
			  shade: 0.3,
			  area: ['90%', '80%'],
			  content: "${ctx}/tag/iconselect?value="+$("#${id}").val(),
			  btn: ['确定','清除', '关闭'],
			  yes: function(index, layero){
              	var layFrame = window[layero.find('iframe')[0]['name']];
            	icon = layFrame.callbackdata();
            	$("#${id}Icon").attr("class", "fa "+icon);
                $("#${id}IconLabel").text(icon);
                $("#${id}").val(icon);
                layui.layer.close(index);
			  },
              btn2: function(index, layero){
                  $("#${id}Icon").attr("class", "icon- hide");
                  $("#${id}IconLabel").text("无");
                  $("#${id}").val("");
              },
			  btn3: function(index, layero){
				  layui.layer.close(index);
			  }
		});
	});
</script>

