angular.module('app.controller', [])

.controller('modal-controller', function($scope, $modal, $modalInstance) {
  $scope.closeModal= function() {
    $modalInstance.dismiss('cancel');
  }
})

.controller('LoginController', function($scope, webfactory, $cookies, $modal, $state){

  $scope.data = {};

  $scope.login = function() {
    if ($scope.data.email == undefined || $scope.data.password == undefined) {
      // If details have not been provided, show an error modal
      $modal.open({
        animation: true,
        templateUrl: 'admin/templates/login-details-missing-modal.html',
        controller: 'modal-controller'
      });
    } else {
      //make API call
      webfactory.login($scope.data).then(function(response) {
        if (response == 1) {
          //Login success
          console.log("login success");
          // Put a cookie in here
          $state.go('console');
        } else {
          //Login failed
          console.log("login failed");
          $modal.open({
            animation: true,
            templateUrl: 'admin/templates/login-failed-modal.html',
            controller: 'modal-controller'
          });
        }
      },
      function() {
        //Login failed
        console.log("login REALLY failed");
        $modal.open({
          animation: true,
          templateUrl: 'admin/templates/login-failed-modal.html',
          controller: 'modal-controller'
        });
      })
    }

  }
})
