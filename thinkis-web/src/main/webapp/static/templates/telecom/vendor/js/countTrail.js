var serviceModule = angular.module("serviceModule", ['ngResource']);

serviceModule.service("MapService", ['$resource', '$q', function ($resource, $q) {
    var resource = $resource('', {}, {
        findSale: {
            method: 'GET',
            isArray: true,
            url: '/usermoves'
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
    $scope.mdnMes = []

    $scope.query = function () {
        map.clearOverlays();
        if (!$scope.mdn) {
            alert("请输入手机号！");
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
                    if(index <= 1){
                     var marker = new BMap.Marker(new BMap.Point(data[0], data[1]));  // 创建标注，为要查询的地方对应的经纬度
                     map.addOverlay(marker);
                     marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
                     marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
                     addFigure(data[0], data[1])
                    }else if(index == 2){
                        $scope.home = data[0];
                        $scope.company = data[1];
                        $scope.worker = data[2]
                        $scope.area1 = data[3]
                        $scope.area2 = data[4]
                        $scope.area3 = data[6]
                    }else{
                         var point = new BMap.Point(data[0], data[1]);
                         points[index-3] = point;
                    }
                })
                var curve = new BMapLib.CurveLine(points, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5});
                map.addOverlay(curve); //添加到地图中
                curve.enableEditing(); //开启编辑功能
                });
    };
}


function addFigure(lon, lat){
    //centre:椭圆中心点,X:横向经度,Y:纵向纬度
        	function add_oval(centre,x,y)
        	{
        		var assemble=new Array();
        		var angle;
        		var dot;
        		var tangent=x/y;
        		for(i=0;i<36;i++)
        		{
        			angle = (2* Math.PI / 36) * i;
        			dot = new BMap.Point(centre.lng+Math.sin(angle)*y*tangent, centre.lat+Math.cos(angle)*y);
        			assemble.push(dot);
        		}
        		return assemble;
        	}
        	var point = new BMap.Point(lon, lat);
        	var oval = new BMap.Polygon(add_oval(point,0.02,0.02), {strokeColor:"black", strokeWeight:3, strokeOpacity:0.5});
            map.addOverlay(oval);
}


 var map = new BMap.Map("allmap");  // 创建Map实例
	map.centerAndZoom("上海",10);      // 初始化地图,用城市名设置地图中心点
	map.enableScrollWheelZoom(true);

//    addFigure(121.519552279,31.2823074967)
	 $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });
