angular.module('app', ['ngCookies'])

.factory('webfactory', ['$http', function ($http) {
    var result = {};

    result.submit = function (userID, clientEmail) {
        var data = {
            userID: userID, clientEmail: clientEmail
        }
        console.log('https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/getUser?userID='.concat(data.userID).concat('&clientEmail=').concat(data.clientEmail));
        return $http.post('https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/getUser?userID='.concat(data.userID).concat('&clientEmail=').concat(data.clientEmail)).then(function (response) {
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
        }, function(error) {
            $scope.data.points = error;
        })
    }
})