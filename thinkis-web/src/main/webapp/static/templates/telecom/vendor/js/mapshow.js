    var map = new BMap.Map("allmap");  // 创建Map实例
	map.centerAndZoom("上海",12);      // 初始化地图,用城市名设置地图中心点
	map.enableScrollWheelZoom(true);
    var point1 = new BMap.Point(121.48548,31.29005),
        point2 = new BMap.Point(121.519552279,31.2823074967),
        point3 = new BMap.Point(121.527010067,31.2735762432);
     var points = [point1, point2,point3];
     for(var i=0; i<3; i++){
    	 <!--var marker = new BMap.Marker(points[i]);-->
    	 <!--map.addOverlay(marker);-->
     }
   	//创建弧线对象
     var curve = new BMapLib.CurveLine(points, {strokeColor:"red", strokeWeight:3, strokeOpacity:0.5});
     map.addOverlay(curve); //添加到地图中
 	 curve.enableEditing(); //开启编辑功能

	 $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });