angular.module('app', ['ngCookies','ui.bootstrap','ngAnimate', 'app.factories'])

.controller('redeem', function ($scope, webfactory, $cookies, $modal) {

  $scope.email = $cookies.get("com.yapnak.email");
  if ($scope.email == undefined || $scope.email== null || $scope.email == "") {
    $modal.open({
      animation: true,
      templateUrl: 'modules/templates/account-not-found-modal.html'
    });
  }
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

.controller('client-controller', function($scope, webfactory, $cookies, $modal){

  console.log($cookies.get("com.yapnak.email"));

  var email = $cookies.get("com.yapnak.email");

  var offer1Changed;
  var offer2Changed;
  var offer3Changed;
  var offer1Active;
  var offer2Active;
  var offer3Active;
  var details = function() {
    webfactory.getInfo(email).then(function(details){
      if (details.status == "True") {
        $scope.name = details.name;

        $scope.offer1text = details.offer1;

        if (details.showOffer1 == 1) {
          $scope.offer1 = true;
        }

        $scope.offer2text = details.offer2;

        if (details.showOffer2 == 1) {
          $scope.offer2 = true;
        }

        $scope.offer3text = details.offer3;

        if (details.showOffer3 == 1) {
          $scope.offer3 = true;
        }

        $scope.foodStyle = details.foodStyle;
        $scope.photo = details.photo;
        $scope.location = details.y + " " + details.x;
        $scope.image = details.photo;
        offer1Changed = $scope.offer1text;
        offer2Changed = $scope.offer2text;
        offer3Changed = $scope.offer3text;
        offer1Active = $scope.offer1;
        offer2Active = $scope.offer2;
        offer3Active = $scope.offer3;
        $scope.newLocation = "";
        $scope.newName = "";
        $scope.newFoodStyle = "";
        $scope.newOffer1text = "";
        $scope.newOffer2text = "";
        $scope.newOffer3text = "";
      } else {
        console.log("false");
        $modal.open({
          animation: true,
          templateUrl: 'modules/templates/account-not-found-modal.html'
        });
      }
    })
  }


  details();

  $scope.toLogin

  $scope.updateInfo = function() {

    if ($scope.newLocation !== "") {
      webfactory.updateLocation($scope.newLocation,email).then(function(response) {
        details();
      });
    }

    if ($scope.newFoodStyle !== "") {
      webfactory.updateType($scope.newFoodStyle,email).then(function(response) {
        details();
      });
    }

    if ($scope.newName !== "") {
      webfactory.updateName($scope.newName,email).then(function(response) {
        details();
      });
    }

    if ($scope.offer1 !== offer1Active) {
      if ($scope.offer1 == false) {
        webfactory.toggleOffer(email, 1, 0);
      } else {
        //check if offer has been placed and then create new offer
        if ($scope.newOffer1text !== "") {
          webfactory.insertOffer(email,1,$scope.newOffer1text).then(function(response){
            webfactory.toggleOffer(email, 1, 1).then(function() {
              details();

            });
          });
        } else {
          webfactory.toggleOffer(email, 1, 1);
        }
      }
    } else {
      if ($scope.newOffer1text !== offer1Changed && $scope.newOffer1text !== "") {
        webfactory.insertOffer(email,1,$scope.newOffer1text).then(function(response){
          webfactory.toggleOffer(email, 1, 1).then(function() {
            details();

          });
        });
      }
    }

    if ($scope.offer2 !== offer2Active) {
      if ($scope.offer2 == false) {
        webfactory.toggleOffer(email, 2, 0);
      } else {
        //check if offer has been placed and then create new offer
        if ($scope.newOffer2text !== "") {
          webfactory.insertOffer(email,2,$scope.newOffer2text).then(function(response){
            webfactory.toggleOffer(email, 2, 1).then(function() {
              details();

            });
          });
        } else {
          webfactory.toggleOffer(email, 2, 1);
        }
      }
    } else {
      if ($scope.newOffer2text !== offer2Changed  && $scope.newOffer2text !== "") {
        webfactory.insertOffer(email,2,$scope.newOffer2text).then(function(response){
          webfactory.toggleOffer(email, 2, 1).then(function() {
            details();

          });
        })
      }
    }

    if ($scope.offer3 !== offer3Active) {
      if ($scope.offer3 == false) {
        webfactory.toggleOffer(email, 3, 0);
      } else {
        //check if offer has been placed and then create new offer
        if ($scope.newOffer3text !== "") {
          webfactory.insertOffer(email,3,$scope.newOffer3text).then(function(response){
            webfactory.toggleOffer(email, 3, 1).then(function() {
              details();

            });
          });
        } else {
          webfactory.toggleOffer(email, 3, 1);
        }
      }
    } else {
      if ($scope.newOffer3text !== offer3Changed  && $scope.newOffer3text !== "") {
        webfactory.insertOffer(email,3,$scope.newOffer3text).then(function(response){
          webfactory.toggleOffer(email, 3, 1).then(function() {
            details();

          });
        });
      }
    }
  }
})
