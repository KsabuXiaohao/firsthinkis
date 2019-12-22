<input id="${id}Id" name="${name}" class="${cssClass!}" type="hidden" value="${value}"/>
<div class="tree-select">
    <input type="text" id="${id}Name" name="${labelName}" value="${labelValue}" class="${id}Name layui-input ${cssClass!} ${has(allowInput)&&(allowInput!=false)?'':'layui-disabled'}" style="${cssStyle!} color:black!important;" lay-verify="${has(required)?'required':''}" placeholder="点击选择" autocomplete="off" class="layui-input">
    <button class="search_btn ${id}Name" type="button"><i class="layui-icon">&#xe615;</i></button>
</div>
<script type="text/javascript">
	$(".${id}Name").click(function(){
		// 正常打开	
		layui.layer.open({
		  type: 2,
		  title: "选择${title}",
		  shadeClose: true,
		  shade: 0.3,
		  area: ['330px', '420px'],
		  content: "${ctx}/tag/treeselect?url="+encodeURIComponent("${url}")+"&module=${module!}&checked=${checked!}&extId=${extId!}&isAll=${isAll!}",
		  btn: ['确定', '关闭'],
		  yes: function(index, layero){
				var tree = window[layero.find('iframe')[0]['name']].tree;
				var ids = [], names = [], nodes = [];
				if ("${checked!}" == "true"){
					nodes = tree.getCheckedNodes(true);
				}else{
					nodes = tree.getSelectedNodes();
				}
				for(var i=0; i<nodes.length; i++) {
                    <%if(has(checked)&&(checked==true)&&has(notAllowSelectParent)&&(notAllowSelectParent==true)){%>
					if (nodes[i].isParent){
						continue; // 如果为复选框选择，则过滤掉父节点
					}
					<%}%>
                    <%if(has(notAllowSelectRoot)&&(notAllowSelectRoot==true)){%>
					if (nodes[i].level == 0){
						layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。");
						return false;
					}
					<%}%>
                    <%if (has(notAllowSelectParent)&&(notAllowSelectParent==true)){%>
					if (nodes[i].isParent){
						layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。");
						return false;
					}
					<%}%>
                    <%if (isNotEmpty(module)&& has(selectScopeModule)&&selectScopeModule==true){%>
					if (nodes[i].module == ""){
						layer.msg("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
						return false;
					}else if (nodes[i].module != "${module}"){
						layer.msg("不能选择当前栏目以外的栏目模型，请重新选择。");
						return false;
					}
                    <%}%>
					ids.push(nodes[i].id);
					names.push(nodes[i].name);
                    <%if (has(checked)&&checked!=true){%>
					break; // 如果为非复选框选择，则返回第一个选择
                    <%}%>
				}
				$("#${id}Id").val(ids.join(",").replace(/u_/ig,""));
				$("#${id}Name").val(names.join(","));
				layui.layer.close(index);
		  },
		  btn2: function(index, layero){
			  layui.layer.close(index);
		  }
		});
	});
</script>