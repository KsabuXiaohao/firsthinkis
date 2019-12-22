var serviceModule = angular.module("serviceModule", ['ngResource']);

serviceModule.service("ExportService", ['$resource', '$q', function ($resource, $q) {
    var resource = $resource('', {}, {
        findExport: {
            method: 'GET',
            isArray: true,
            url: '/exports'
        }

    });

    return {
        findExport: function (meid) {
            var d = $q.defer();
            resource.findExport({
                                  meid: meid
                              }, function (result) {
                d.resolve(result);
            }, function (result) {
                d.reject(result);
            });
            return d.promise;
        }
    }
}]);

var exportModule = angular.module('export', ['serviceModule']);

exportModule.controller("exportController", ['$scope', '$location', 'ExportService', controller]);

function controller($scope, $location, ExportService) {
    $scope.exports = [];

    $scope.query = function () {
        if (!$scope.meid) {
            alert("请输入IMEI号");
            return;
        }

//
//         if (!$scope.offset2) {
//                    alert("请输入查询最近几天的数据");
//                    return;
//                }

        ExportService.findExport($scope.meid)
            .then(function (result) {
                $scope.exports = result;
            })

    };



}
