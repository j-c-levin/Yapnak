angular.module('app', ['ngCookies'])

.factory('webfactory', ['$http', function ($http) {
    var result = {};

    result.submit = function (userID, clientEmail) {

        var data = {
            userID: userID, clientEmail: clientEmail
        }

        return $http.post('https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/getUser?userID='.concat(data.userID).concat('&clientEmail=').concat(data.clientEmail)).then(function (response) {
            return response.data;
        }, function (error) {
            return error;
        })
    };

    result.forgot = function (email) {
        var req = {
            method: 'POST',
            url: 'https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/forgotLogin',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: {
                email: email
            }
        }
        return $http(req).then(function (response) {

            return response.data.status;

        }, function (error) {
            return error;
        })
    };

    result.reset = function (password, hash) {
        var req = {
            method: 'POST',
            url: 'https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/resetPassword',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
            data: {
                password: password,
                hash: hash
            }
        }
        return $http(req).then(function (response) {
            return response.data.status;
        }, function (error) {
            console.log(error.data.message);
            return error.data.status;
        });
    };

    return result;
}])

.controller('main', function ($scope, webfactory, $cookies) {

    $scope.email = $cookies.get("com.yapnak.email");

    $scope.data = {};

    $scope.submit = function () {
        $scope.userFound = "searching";
        webfactory.submit($scope.text, $scope.email).then(function (response) {
            if (response == "") {
                $scope.userFound = false;
            }
            else {
                $scope.data.points = response.points;
                $scope.userFound = true;
            }
        }, function (error) {
            $scope.data.points = error;
        })
    }
})

.controller('forgot-controller', function ($scope, webfactory) {

    $scope.submit = function () {
        if ($scope.email !== undefined) {
            $scope.response = "Searching for your account...";
            webfactory.forgot($scope.email).then(function (response) {
                console.log(response);
                $scope.submitted = true;
                if (response == "True") {
                    $scope.response = "An emails has been sent with details."
                } else {
                    $scope.response = "That email isn't registered."
                }
            }, function (error) {
                console.log(error);
                $scope.submitted = true;
                $scope.response = "That email isn't registered."
            })
        } else {
            $scope.response = "That email isn't registered."
        }
    }
})

.controller('reset-controller', function ($window, $timeout, $scope, webfactory, $cookies) {
    $scope.valid = true;
    $scope.hash = $cookies.get("com.yapnak.hash");

    if ($scope.hash == undefined) {
        $scope.response = "You do not have permission to view this page."
        $scope.valid = false;
    }

    $scope.submit = function () {
        if ($scope.valid == true) {
            if ($scope.pass == $scope.cPass) {
                webfactory.reset($scope.pass, $scope.hash).then(function (response) {
                    if (response == "True") {
                        $scope.response = "Password changed.  Log in at yapnak.com/client";
                    } else {
                        $scope.response = "Something went wrong, sorry."
                    }
                }, function (error) {
                    $scope.response = "Something went wrong, sorry."
                })

            } else {
                console.log($scope.cPass.concat(" ").concat($scope.pass));
                $scope.response = "Your passwords are not the same."
            }
        }
    }
})