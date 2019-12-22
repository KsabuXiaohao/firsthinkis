<%
	/**
	 	文件上传组件参数说明：
	 	1、id:文件唯一标识
	 	2、name:对象附件保存属性名
	 	3、value:对象附件属性值
	 	4、fileType：允许上传文件类型
	 	5、cssClass：附件显示样式
	 	6、height\width:图片文件显示宽高
	 	7、filename：自定义文件名称
	 	8、type:文件类型分类
	 	9、uploadPath：文件上传目录
	 	10、readonly:是否只读
	 	12、required:是否必填
	 	desc:附件描述
	 **/
%>
<input type="hidden" id="${id}" name="${name}" value="${value}" <%if(required == "true"){%>lay-verify="required"<%}%> />
<div style="min-height: 36px;" id="${id}_elem">
	<div id="${id}_view" style="line-height: 36px;margin-left: 10px;">
		<%if(isNotEmpty(value)){%>
		<span>${filename}</span>
		&nbsp;&nbsp;<a class="layui-blue" onclick="${id}deleteFile(this)"><i class="fa fa-trash-o"></i> 删除文件</a>
		<%}else{%>
		<a class="layui-blue" ><i class="fa fa-upload"></i> 上传附件</a>
		<%}%>
	</div>
	<p style="line-height: 36px;margin-left: 10px;color: #bbbbbb;"><i class="fa fa-info-circle"></i>  ${has(desc)?desc:'支持文件类型，最大上传限制'}</p>
</div>
<script id="filesTpl" type="text/html">
	<span id="{{d.id}}">{{ d.fileName }}</span>
	&nbsp;&nbsp;<a class="layui-blue" onclick="${id}deleteFile(this)"><i class="fa fa-trash-o"></i> 删除文件</a>
</script>
<script>
    layui.use(['form','upload','laytpl','element'], function() {
        var form = layui.form,upload=layui.upload,laytpl = layui.laytpl,element=layui.element;
        //拖拽上传
        upload.render({
            elem: '#${id}_elem'
            ,field:'attach'
            ,multiple: false
            ,url: '${ctx}/file/uploadFile'
            ,accept: 'file' //普通文件
            ,exts: '${fileType}' //只允许上传压缩文件
			,size:${has(size)?size:100} //限制文件大小，单位 KB
            ,before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
                layer.load(); //上传loading
            }
            ,done: function(res){
                layer.closeAll('loading'); //关闭loading
                if(res.state=="SUCCESS"){
                    $("#${id}").val(res.fileId);
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

        ${id}deleteFile = function(obj){
            event.stopPropagation();
            layer.confirm('确定删除选中文件？',{icon:3, title:'确认信息'},function(index){
                $("#${id}").val("");
				$(obj).remove();
				$('#${id}_view').html('<a class="layui-blue" ><i class="fa fa-upload"></i> 上传附件</a>');
				element.init();
				layer.close(index);
			});
        };

        ${id}preView = function(href,event) {
            event.stopPropagation();
            window.location = href;
        }
    });
</script>