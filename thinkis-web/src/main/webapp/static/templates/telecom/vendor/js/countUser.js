var serviceModule = angular.module("serviceModule", ['ngResource']);

serviceModule.service("MapService", ['$resource', '$q', function ($resource, $q) {
    var resource = $resource('', {}, {
        findSale: {
            method: 'GET',
            isArray: true,
            url: '/sales'
        }
    });

    return {
        findSale: function (lon,lat,firsttime,lasttime) {
            var d = $q.defer();
            resource.findSale({
                                  lon: lon,
                                  lat: lat,
                                  firsttime: firsttime,
                                  lasttime: lasttime
                              }, function (result) {
                d.resolve(result);
            }, function (result) {
                d.reject(result);
            });
            return d.promise;
        }
    }
}]);


var mapModule = angular.module('map', ['serviceModule']);
var dataArray = new Array()

mapModule.controller("mapController", ['$scope', '$location', 'MapService', controller]);

function controller($scope, $location, MapService) {
    $scope.sales = [];

    $scope.query = function () {
        map.clearOverlays();
        if (!$scope.place) {
            alert("请输入地点！");
            return;
        }
        if (!$scope.firsttime) {
            alert("请输入开始时间！");
            return;
        }

         if (!$scope.lasttime) {
                    alert("请输入结束时间！");
                    return;
                }

         localSearch.setSearchCompleteCallback(function (searchResult) {

                  var searchPoint = new Array()
                  var poi = searchResult.getPoi(0);
                  var lon = poi.point.lng;
                  var lat = poi.point.lat;
                  map.centerAndZoom(poi.point, 13);
                  var opts = {
                      width : 20,     // 信息窗口宽度
                      height: 20,     // 信息窗口高度
                      title : "手机号：" , // 信息窗口标题
                      enableMessage:true//设置允许信息窗发送短息
                    };
                  var marker = new BMap.Marker(new BMap.Point(lon, lat));  // 创建标注，为要查询的地方对应的经纬度
                  map.addOverlay(marker);
                  marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
                  marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                  searchPoint[0] = lon;
                  searchPoint[1] = lat
                  MapService.findSale(lon,lat,$scope.firsttime,$scope.lasttime)
                              .then(function (result) {
                                  $scope.sales = result;
                                  angular.forEach($scope.sales, function(data,index,array){
                                      var point = new BMap.Point(data[1],data[2]);
                                      points[index] = point;
                                      var opts = {
                                              				width : 20,     // 信息窗口宽度
                                              				height: 20,     // 信息窗口高度
                                              				title : "手机号：" , // 信息窗口标题
                                              				enableMessage:true//设置允许信息窗发送短息
                                              			   };

                                              var marker = new BMap.Marker(point);  // 创建标注
                                              var content = data[0]+" "+"距离："+data[3]+"米";
                                              map.addOverlay(marker);               // 将标注添加到地图中

                                              addClickHandler(content,marker)
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
                                  })
                                  });
              });
          localSearch.search($scope.place);
    };
}

function addMarker(){
    for(data in dataArray){
            var point = new BMap.Point(dataArray[data].lon, dataArray[data].lat);
            var marker = new BMap.Marker(point);
            map.addOverlay(marker);
     }
}

function addCurve(){
    var points = new Array();
    for(data in dataArray){
             var point = new BMap.Point(dataArray[data].lon, dataArray[data].lat);
             points[data] = point
         }
    var curve = new BMapLib.CurveLine(points, {strokeColor:"red", strokeWeight:3, strokeOpacity:0.5});
    map.addOverlay(curve); //添加到地图中
    curve.enableEditing(); //开启编辑功能
}

function showUser(data_info){
         var data_info = [[121.48548,31.29005,"100"],
         					 [121.519552279,31.2823074967,"18516566108"],
         					 [121.527010067,31.2735762432,"18605561372"]
         					];
        var opts = {
        				width : 20,     // 信息窗口宽度
        				height: 20,     // 信息窗口高度
        				title : "手机号：" , // 信息窗口标题
        				enableMessage:true//设置允许信息窗发送短息
        			   };
        for(var i=0;i<data_info.length;i++){
        		var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
        		var content = data_info[i][2];
        		map.addOverlay(marker);               // 将标注添加到地图中
        		addClickHandler(content,marker);
        	}
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
}

 var map = new BMap.Map("allmap");  // 创建Map实例
	map.centerAndZoom("上海",10);      // 初始化地图,用城市名设置地图中心点
	map.enableScrollWheelZoom(true);

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小

    var point1 = new BMap.Point(121.48548,31.29005),
        point2 = new BMap.Point(121.519552279,31.2823074967),
        point3 = new BMap.Point(121.527010067,31.2735762432);
     var points = [point1, point2,point3];


	 $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });
