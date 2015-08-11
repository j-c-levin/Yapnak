angular.module('app', ['ngCookies'])

.factory('webfactory', ['$http', function ($http) {
    var result = {};

    result.submit = function (userID, clientEmail) {
        var data = {
            userID: userID, clientEmail: clientEmail
        }
        return $http.post('https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/getUser/'.concat(data.userID).concat('/').concat(data.clientEmail)).then(function (response) {
            return response.data;
        }, function (error) {
            return error;
        })
    };

    return result;
}])

.controller('main', function ($scope, webfactory, $cookies ) {

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
        })
    }
})