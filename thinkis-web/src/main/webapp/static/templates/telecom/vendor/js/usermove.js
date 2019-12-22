var serviceModule = angular.module("serviceModule", ['ngResource']);

serviceModule.service("UsermoveService", ['$resource', '$q', function ($resource, $q) {
    var resource = $resource('', {}, {
        findUsermove: {
            method: 'GET',
            isArray: true,
            url: '/usermoves'
        }

    });

    return {
        findUsermove: function (mdn,firsttime,lasttime) {
            var d = $q.defer();
            resource.findUsermove({
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

var usermoveModule = angular.module('usermove', ['serviceModule']);

usermoveModule.controller("usermoveController", ['$scope', '$location', 'UsermoveService', controller]);

function controller($scope, $location, UsermoveService) {
    $scope.usermoves = [];

    $scope.query = function () {
        if (!$scope.mdn) {
            alert("请输入用户号码");
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

        UsermoveService.findUsermove($scope.mdn,$scope.firsttime,$scope.lasttime)
            .then(function (result) {
                $scope.usermoves = result;
            })

    };



}
