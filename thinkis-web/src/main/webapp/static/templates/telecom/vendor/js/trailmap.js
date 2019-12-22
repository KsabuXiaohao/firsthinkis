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
        findSale: function (mdn,firsttime,lasttime) {
            var d = $q.defer();
            resource.findSale({
                                  mdn: mdn,
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
        if (!$scope.mdn) {
            alert("请输入用户号码!");
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

        MapService.findSale($scope.mdn,$scope.firsttime,$scope.lasttime)
            .then(function (result) {
                $scope.sales = result;
                var points = new Array();
                angular.forEach($scope.sales, function(data,index,array){
                    var point = new BMap.Point(data.lon, data.lat);
                    points[index] = point;
                })
                var curve = new BMapLib.CurveLine(points, {strokeColor:"red", strokeWeight:3, strokeOpacity:0.5});
                map.addOverlay(curve); //添加到地图中
                curve.enableEditing(); //开启编辑功能
                });
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


	 $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });
