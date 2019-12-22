var serviceModule = angular.module("serviceModule", ['ngResource']);

serviceModule.service("PositionService", ['$resource', '$q', function ($resource, $q) {
    var resource = $resource('', {}, {
        findPosition: {
            method: 'GET',
            isArray: true,
            url: '/positions'
        }

    });

    return {
        findPosition: function (imsi, begin, end) {
            var d = $q.defer();
            resource.findPosition({
                                  imsi: imsi,
                                  begin: begin,
                                  end: end
                              }, function (result) {
                d.resolve(result);
            }, function (result) {
                d.reject(result);
            });
            return d.promise;
        }
    }
}]);

var positionModule = angular.module('position', ['serviceModule']);

positionModule.controller("positionController", ['$scope', '$location', 'PositionService', controller]);

function controller($scope, $location, PositionService) {
    $scope.positions = [];

    $scope.query = function () {
        if (!$scope.imsi) {
            alert("请输入IMSI号");
            return;
        }
        if (!$scope.begin) {
                   alert("请输入起始时间");
                   return;
        }
        if (!$scope.end) {
                   alert("请输入结束时间");
                   return;
        }


        PositionService.findPosition($scope.imsi,$scope.begin,$scope.end)
            .then(function (result) {

                $scope.positions = result;
                	// 百度地图API功能
                var markers = [];
                var points = new Array();
                                angular.forEach($scope.positions, function(data,index,array){
                                    var point = new BMap.Point(data.longitude, data.latitude);

                                    points[index] = point;
                                    markers.push(new BMap.Marker(point));
                                    //map.addOverlay(marker);
                                })

                var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers});



            })



    };



}
var map = new BMap.Map("allmap");  // 创建Map实例
map.centerAndZoom("上海",12);      // 初始化地图,用城市名设置地图中心点
map.enableScrollWheelZoom(true);

	 $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii",
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });











