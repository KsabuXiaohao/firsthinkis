<%
	/**
	 	文件上传组件参数说明：
	 	1、id:文件唯一标识
	 	2、name:对象附件保存属性名
	 	3、value:对象附件属性值
	 	4、height\width:图片文件显示宽高
	 	5、uploadPath：文件上传目录
	 	6、readonly:是否只读
	 	7、required:是否必填
	 **/
%>
<div style="padding-left: 10px;">
	<input type="hidden" id="${id}" name="${name}" value="${value}" <%if(required == "true"){%>lay-verify="required"<%}%> />
	<div class="layui-upload-drag" style="padding: 10px;" id="${id}_elem">
		<ul id="${id}_view">
			<%if(isNotEmpty(value)){%>
				<li>
					<img src="${value}" class="layui-circle"  style="height: ${height};width: ${width};">
				</li>
			<%}else{%>
				<li style="line-height: 40px;padding-left: 10px;"><span>头像</span></li>
			<%}%>
		</ul>
		<p>点击上传，或将文件拖拽到此处</p>
	</div>
</div>
<script id="filesTpl" type="text/html">
	<li>
		<img src="{{d.url}}" class="layui-circle"  style="height: ${height};width: ${width};">
	</li>
</script>

<script>
    layui.use(['form','upload','laytpl','element'], function() {
        var form = layui.form,upload=layui.upload,laytpl = layui.laytpl,element=layui.element;
        //拖拽上传
        upload.render({
            elem: '#${id}_elem'
            ,field:'attach'
            ,multiple: false
            ,url: '${ctx}/file/uploadFile?type=images'
            ,accept: 'file' //普通文件
            ,exts: 'jpg|jpeg|png' //只允许上传压缩文件
            ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(); //上传loading
            }
            ,done: function(res){
                layer.closeAll('loading'); //关闭loading
				console.log(res);
                if(res.state=="SUCCESS"){
                    $("#${id}").val(res.url);
                    var getTpl = document.getElementById('filesTpl').innerHTML
                        ,${name}_view = document.getElementById('${id}_view');
                    laytpl(getTpl).render(res, function(html){
                        ${id}_view.innerHTML = html;
                    });
                    layer.msg("文件上传成功");
                }else{
                    layer.msg("文件上传失败");
                }
            }
            ,error:function(){
                layer.closeAll('loading'); //关闭loading
                layer.msg("文件上传失败");
            }
        });
    });
</script>