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
        findSale: function (mdn) {
            var d = $q.defer();
            resource.findSale({
                        mdn: mdn
                     }, function (result) {
                d.resolve(result);
            }, function (result) {
                d.reject(result);
            });
            return d.promise;
        }
    }
}]);

var saleModule = angular.module('map', ['serviceModule']);

saleModule.controller("mapController", ['$scope', '$location', 'MapService', controller]);

function controller($scope, $location, MapService) {
    $scope.sales = [];

    $scope.query = function () {
        if (!$scope.mdn) {
            alert("请输入手机号");
            return;
        }
//        if (!$scope.offset1) {
//            alert("请输入查询最近几天的数据");
//            return;
//        }
//
//         if (!$scope.offset2) {
//                    alert("请输入查询最近几天的数据");
//                    return;
//                }
        SaleService.findSale($scope.mdn)
            .then(function (result) {
                $scope.data = result;
            })

    };
}