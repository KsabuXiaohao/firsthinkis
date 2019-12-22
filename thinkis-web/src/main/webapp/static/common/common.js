/*一些基础的js方法*/
//日期格式化
Date.prototype.format=function(format){var d=this,o={"M+":d.getMonth()+1,"d+":d.getDate(),"H+":d.getHours(),"m+":d.getMinutes(),"s+":d.getSeconds(),w:["日","一","二","三","四","五","六"][d.getDay()]};if(/(y+)/.test(format)){format=format.replace(RegExp.$1,(d.getFullYear()+"").substr(4-RegExp.$1.length))}for(var k in o){if(new RegExp("("+k+")").test(format)){format=format.replace(RegExp.$1,RegExp.$1.length==1?o[k]:("00"+o[k]).substr((""+o[k]).length))}}return format};

var Common = {
    log: function (info) {
        console.log(info);
    },
    alert: function (info, iconIndex) {
        top.layer.msg(info, {icon: iconIndex});
    },
    message: function(info){
    	top.layer.msg(info);
    },
    info: function (info) {
        Common.message(info);
    },
    success: function (info) {
        Common.message(info);
    },
    error: function (info) {
		Common.openConfirm(info)
    },
    loading:function(info){
    	return top.layer.msg(info,{icon: 16,time:false,shade:0.8});
    },
    openDlg:function(url,title){
		var index = layui.layer.open({  
	        type: 2,  
	        content: url,  
	        title: title,  
	        maxmin: false,
	        area: ['90%', '80%']
	    });  
    },
    openConfirm:function(content,callback,callBackNo){
    	var index = layui.layer.confirm(content, {icon: 3, title: '确认信息'}, {
    		  btn: ['确认','取消'] //按钮
    		}, function(){
    		    if(callback!=null){
    		        callback();
    		    }
    			top.layer.close(index);
    		}, function(){
    		    if(callBackNo!=null){
    		        callBackNo();
    		    }
    			top.layer.close(index);
		});
		
    },
    openPrompt:function(title,defaultValue,callback){
    	layui.layer.prompt({title: title, formType: 0,value:defaultValue}, function(value, index,elem){
		  layer.close(index);
		  callback(value);
		});
    }
};