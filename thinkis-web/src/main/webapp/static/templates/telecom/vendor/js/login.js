var serviceModule = angular.module("serviceModule", ['ngResource']);

var welcomeUserValue = "";
var testPassword =/^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\d)(?=.*?[!#@*&.])[a-zA-Z\d!#@*&.]*$/;
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
            alert("请输入用户名！");
            return;
        }
        if (!$scope.password) {
            alert("请输入密码！");
            return;
        }
        UserService.findUser($scope.username,$scope.password)
            .then(function (result) {
                $scope.users = result;
                var len = $scope.users.length;
                if (!testPassword.test($scope.password)) {//登录时判断密码规则是否为表达式所定制的规则
                    alert("登录密码不符合规则，请修改密码！。规则为：（满足大写字母，小写字母，数字和特殊字符！！）");
                    return;
                }else if($scope.users[0].status!="0"){//判断是否为旧密码，如果是旧密码不论是否为弱密码都必须从新修改密码，保证密码都会被加密为MD5
                    alert("登录密码不符合规则，请修改密码！。规则为：（满足大写字母，小写字母，数字和特殊字符！！）");
                    return;
                }
                if(len == 0){
                    alert("用户名或密码错误！");
                    return;
                } else{
                    window.location.href="http://www.one-touch.com.cn/inner.html?username="+$scope.username;
                    window.event.returnValue=false;
                }
            })

    };
}