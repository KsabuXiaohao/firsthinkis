function setMapList(){
    // $(document).ready(function (){
    $.ajax({
        type: "post",
        url: "${ctxFront}/telecom/homemeasure/getList",
        data: "",
        dataType: "json",
        success: function(json){
            // alert(data)
            var markers = [];
            angular.forEach(json, function(data,index,array){

                var pt = new BMap.Point(data.longitude, data.latitude);
                var myIcon;
                var flag;
                if(data.snr < 0)
                {
                    myIcon = new BMap.Icon("/vendor/pic/bullet_red.png", new BMap.Size(32,32));
                    flag = "red";
                }
                else if(data.snr >= 0 && data.snr < 100)
                {
                    myIcon = new BMap.Icon("/vendor/pic/bullet_blue.png", new BMap.Size(32,32));
                    flag = "blue";
                }
                else if(data.snr >= 100 && data.snr < 200)
                {
                    myIcon = new BMap.Icon("/vendor/pic/bullet_orange.png", new BMap.Size(32,32));
                    flag = "orange";
                }
                else
                {
                    myIcon = new BMap.Icon("/vendor/pic/bullet_green.png", new BMap.Size(32,32));
                    flag = "green";
                }
                //myIcon = new BMap.Icon("/vendor/pic/bullet_green.png", new BMap.Size(32,32));

                var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
                var label = new BMap.Label(flag);
                label.setStyle({ display : "none" });
                marker2.setLabel(label);
                var cont = "时间：" + data.savetime +
                    "<br/>地点：" + data.location +
                    "<br/>经度：" + data.longitude +
                    "<br/>纬度：" + data.latitude +
                    "<br/>SNR：" + data.snr/10 +
                    "<br/>RSRP：" + data.signalpower/10 +
                    "<br/>PCI：" + data.pci +
                    "<br/>CellID：" + data.cid +
                    "<br/>频点：" + data.earfcn +
                    "<br/>tx_power：" + data.tx_power +
                    "<br/>tx_time：" + data.tx_time +
                    "<br/>rx_time：" + data.rx_time +
                    "<br/>IP地址：" + data.ip +
                    "<br/>IMSI：" + data.imsi +
                    "<br/>IMEI：" + data.meid;

                addClickHandler(cont,marker2);


                markers.push(marker2);
                //map.addOverlay(marker2);
            })
            var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});
        }
    });
}
var map = new BMap.Map("allmap",{enableMapClick:false});
map.centerAndZoom("上海", 11);  // 初始化地图,设置中心点坐标和地图级别
map.enableScrollWheelZoom();
map.enableInertialDragging();

map.enableContinuousZoom();

var size = new BMap.Size(10, 20);
map.addControl(new BMap.CityListControl({
    anchor: BMAP_ANCHOR_TOP_LEFT,
    offset: size,
    // 切换城市之间事件
    // onChangeBefore: function(){
    //    alert('before');
    // },
    // 切换城市之后事件
    // onChangeAfter:function(){
    //   alert('after');
    // }
}));

// 定义一个控件类,即function
function ZoomControl1(){
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
    this.defaultOffset = new BMap.Size(10, 40);
}

// 通过JavaScript的prototype属性继承于BMap.Control
ZoomControl1.prototype = new BMap.Control();

// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
ZoomControl1.prototype.initialize = function(map){
    // 创建一个DOM元素
    var div = document.createElement("div");
    // 添加文字说明
    div.appendChild(document.createTextNode("优秀"));
    // 设置样式
    div.style.cursor = "pointer";
    div.style.border = "1px solid gray";
    div.style.backgroundColor = "green";
    // 绑定事件,点击一次放大两级
    div.onclick = function(e){
        var allOverlay = map.getOverlays();
        console.log(allOverlay.length);
        for (var i = 0; i < allOverlay.length -1; i++){

            if (allOverlay[i].toString() == "[object Marker]") {
                console.log(allOverlay[i]);
                if(allOverlay[i].getLabel().content == "green"){
                    map.removeOverlay(allOverlay[i]);
                }
            }
        }
    }
    // 添加DOM元素到地图中
    map.getContainer().appendChild(div);
    // 将DOM元素返回
    return div;
}

// 定义一个控件类,即function
function ZoomControl2(){
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
    this.defaultOffset = new BMap.Size(10, 70);
}

// 通过JavaScript的prototype属性继承于BMap.Control
ZoomControl2.prototype = new BMap.Control();

// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
ZoomControl2.prototype.initialize = function(map){
    // 创建一个DOM元素
    var div = document.createElement("div");
    // 添加文字说明
    div.appendChild(document.createTextNode("良好"));
    // 设置样式
    div.style.cursor = "pointer";
    div.style.border = "1px solid gray";
    div.style.backgroundColor = "orange";
    // 绑定事件,点击一次放大两级
    div.onclick = function(e){
        map.setZoom(map.getZoom() + 2);
    }
    // 添加DOM元素到地图中
    map.getContainer().appendChild(div);
    // 将DOM元素返回
    return div;
}

// 定义一个控件类,即function
function ZoomControl3(){
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
    this.defaultOffset = new BMap.Size(10, 100);
}

// 通过JavaScript的prototype属性继承于BMap.Control
ZoomControl3.prototype = new BMap.Control();

// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
ZoomControl3.prototype.initialize = function(map){
    // 创建一个DOM元素
    var div = document.createElement("div");
    // 添加文字说明
    div.appendChild(document.createTextNode("中等"));
    // 设置样式
    div.style.cursor = "pointer";
    div.style.border = "1px solid gray";
    div.style.backgroundColor = "blue";
    // 绑定事件,点击一次放大两级
    div.onclick = function(e){
        map.setZoom(map.getZoom() + 2);
    }
    // 添加DOM元素到地图中
    map.getContainer().appendChild(div);
    // 将DOM元素返回
    return div;
}

// 定义一个控件类,即function
function ZoomControl4(){
    // 默认停靠位置和偏移量
    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
    this.defaultOffset = new BMap.Size(10, 130);
}

// 通过JavaScript的prototype属性继承于BMap.Control
ZoomControl4.prototype = new BMap.Control();

// 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
// 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
ZoomControl4.prototype.initialize = function(map){
    // 创建一个DOM元素
    var div = document.createElement("div");
    // 添加文字说明
    div.appendChild(document.createTextNode("较差"));
    // 设置样式
    div.style.cursor = "pointer";
    div.style.border = "1px solid gray";
    div.style.backgroundColor = "red";
    // 绑定事件,点击一次放大两级
    div.onclick = function(e){
        map.setZoom(map.getZoom() + 2);
    }
    // 添加DOM元素到地图中
    map.getContainer().appendChild(div);
    // 将DOM元素返回
    return div;
}


// 创建控件
var myZoomCtrl1 = new ZoomControl1();
var myZoomCtrl2 = new ZoomControl2();
var myZoomCtrl3 = new ZoomControl3();
var myZoomCtrl4 = new ZoomControl4();
// 添加到地图当中
map.addControl(myZoomCtrl1);
map.addControl(myZoomCtrl2);
map.addControl(myZoomCtrl3);
map.addControl(myZoomCtrl4);

var opts = {
    width : 0,     // 信息窗口宽度
    height: 0,     // 信息窗口高度
    title : "信息窗口" , // 信息窗口标题
    enableMessage:true//设置允许信息窗发送短息
};
function addClickHandler(content,marker){
    marker.addEventListener("click",function(e){
        openInfo(content,e)}
    );
}
function openInfo(content,e){
    var p = e.target;
    var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
    var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
    map.openInfoWindow(infoWindow,point); //开启信息窗口
}

$(".form_datetime").datetimepicker({

    format: "yyyy-mm-dd hh:ii",
    autoclose: true,
    todayBtn: true,
    pickerPosition: "bottom-left"
});