<input id="${id}" name="${name}" type="hidden" value="${value}"/>
<script type="text/javascript">
	$(document).ready(function() {
		var orderBy = $("#${id}").val().split(" ");
		$(".sort-column").each(function(){
			if ($(this).hasClass(orderBy[0])){
				orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
				$(this).html($(this).html()+" <i class=\"icon icon-arrow-"+orderBy[1]+"\"></i>");
			}
		});
		$(".sort-column").click(function(){
			var order = $(this).attr("class").split(" ");
			var sort = $("#${id}").val().split(" ");
			for(var i=0; i<order.length; i++){
				if (order[i] == "sort-column"){order = order[i+1]; break;}
			}
			if (order == sort[0]){
				sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
				$("#${id}").val(order+" DESC"!=order+" "+sort?"":order+" "+sort);
			}else{
				$("#${id}").val(order+" ASC");
			}
			${callback}
		});
	});
</script>