angular.module('app.controller', [])

.controller('modal-controller', function($scope, $modal, $modalInstance) {
  $scope.closeModal= function() {
    $modalInstance.dismiss('cancel');
  }
})

.controller('LoginController', function($scope, webfactory, $modal, $state){

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

.controller('ConsoleController', function($scope, webfactory, $modal) {

  $scope.gotDetails = "";

  $scope.clientList = [];

  $scope.clientData = {};

  $scope.modal;

  webfactory.getAllClients().then(function(response) {
    if (response !== -1) {
      $scope.clientList = response.clientList;
    }
  });

  $scope.closeModal= function() {
    $scope.modal.close();
  }

  $scope.confirmUpdate = function() {
    console.log("called");
  }

  $scope.update = function() {
    $scope.modal = $modal.open({
      animation: true,
      templateUrl: 'admin/templates/confirm-admin-update-modal.html',
      scope: $scope
    });
  }

  $scope.retrieveClient = function() {
    webfactory.retrieveClient($scope.chosenClient.clientId).then(function(response) {
      if (response !== -1) {
        $scope.clientData = response;
        $scope.gotDetails = "client";
        $scope.clientData.locationText = $scope.clientData.x + " " + $scope.clientData.y;

      ($scope.clientData.isActive == 1) ? $scope.isActive = true : $scope.isActive = false;
      ($scope.clientData.showOffer1 == 1) ? $scope.offer1 = true : $scope.offer1 = false;
      ($scope.clientData.showOffer2 == 1) ? $scope.offer2 = true : $scope.offer2 = false;
      ($scope.clientData.showOffer3 == 1) ? $scope.offer3 = true : $scope.offer3 = false;
      }
    });
  };
})
