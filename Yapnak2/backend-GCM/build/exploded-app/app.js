angular.module('app', [])

.factory('webfactory', ['$http', function ($http) {
    var result = {};

    result.submit = function (userID, clientEmail) {
        var data = {
            userID: userID, clientEmail: 'joshua.c.levin@gmail.com'
        }
        return $http.post('https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/sqlentity/'.concat(data.userID).concat('/').concat(data.clientEmail)).then(function (response) {
            return response.data;
        }, function (error) {
            console.log("called");
            return error;
        })
    };

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