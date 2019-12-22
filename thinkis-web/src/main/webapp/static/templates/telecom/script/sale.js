var serviceModule = angular.module("serviceModule", ['ngResource']);

serviceModule.service("SaleService", ['$resource', '$q', function ($resource, $q) {
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
                                  lat : lat,
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

var saleModule = angular.module('sale', ['serviceModule']);

saleModule.controller("saleController", ['$scope', '$location', 'SaleService', controller]);

function controller($scope, $location, SaleService) {
    $scope.sales = [];

    $scope.query = function () {
        if (!$scope.lon) {
            alert("请输入经度");
            return;
        }
         if (!$scope.lat) {
                    alert("请输入纬度");
                    return;
          }
        if (!$scope.firsttime) {
            alert("请输入查询最近几天的数据");
            return;
        }

         if (!$scope.lasttime) {
                    alert("请输入查询最近几天的数据");
                    return;
                }
//
//         if (!$scope.offset2) {
//                    alert("请输入查询最近几天的数据");
//                    return;
//                }

        SaleService.findSale($scope.lon,$scope.lat,$scope.firsttime,$scope.lasttime)
            .then(function (result) {
                $scope.sales = result;
            })

    };



}