angular.module('app', [])

.factory('webfactory', ['$http', function ($http) {
    var result = {};

    result.submit = function (userID, clientEmail) {
        return $http.post('https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/getUser/'.concat(userID).concat('/').concat(clientEmail)).then(function (response) {
            return response.data;
        }, function (error) {
            console.log("called");
            return error;
        })
    };

    result.forgot = function (email) {
        var req = {
            method: 'POST',
            url: 'https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/',
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
                "grant_type": "password", "username": email, "password": password
            }
        }
        return $http(req).then(function (response) {

        }, function (error) {

        });
    }

    return result;
}])

.controller('main', function ($scope, webfactory) {

    $scope.data = {};

    $scope.submit = function () {
        $scope.userFound = "searching";
        webfactory.submit($scope.text, 'joshua.c.levin@gmail.com').then(function (response) {
            if (response == "") {
                $scope.userFound = false;
            }
            else {
                $scope.data.points = response.points;
                $scope.userFound = true;
            }
        })
    }
})

.controller('forgot-controller', function ($scope, webfactory) {

    $scope.submit = function () {
        if ($scope.email.length > 3) {
            webfactory.forgot($scope.email).then(function (response) {
                $scope.submitted = true;
                $scope.response = "An emails has been sent with details."
            }, function (error) {
                console.log(error);
                $scope.response = "That email isn't registered."
            })

        }
    }
})