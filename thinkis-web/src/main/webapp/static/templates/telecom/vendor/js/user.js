var serviceModule = angular.module("serviceModule", ['ngResource']);

serviceModule.service("UserService", ['$resource', '$q', function ($resource, $q) {
    var resource = $resource('', {}, {
        findUser: {
            method: 'GET',
            isArray: true,
            url: '/users'
        }

    });

    return {
        findUser: function (username,password) {
            var d = $q.defer();
            resource.findUser({
                                  username: username,
                                  password: password
                              }, function (result) {
                d.resolve(result);
            }, function (result) {
                d.reject(result);
            });
            return d.promise;
        }
    }
}]);

var userModule = angular.module('user', ['serviceModule']);

userModule.controller("userController", ['$scope', '$location', 'UserService', controller]);

function controller($scope, $location, UserService) {
    $scope.users = [];

    $scope.query = function () {
        if (!$scope.username) {
            alert("请输入用户名");
            return;
        }
        if (!$scope.password) {
            alert("请输入查询最近几天的数据");
            return;
        }

        UserService.findUser($scope.username,$scope.password)
            .then(function (result) {
                $scope.users = result;
                if(result == "0"){
                    alert("用户名或密码错误！")
                } else{
                    window.location="http://localhost:8080/trailmap.html";
                }
            })

    };
}