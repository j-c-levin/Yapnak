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

  $scope.offers1 = [];
  $scope.offers2 = [];
  $scope.offers3 = [];

  var details = function() {
    webfactory.getInfo(email).then(function(details){
      if (details.status == "True") {
        $scope.name = details.name;

        $scope.clientId = details.id;

        // $scope.offer1text = details.offer1;

        if (details.showOffer1 == 1) {
          $scope.offer1 = true;
        }

        // $scope.offer2text = details.offer2;

        if (details.showOffer2 == 1) {
          $scope.offer2 = true;
        }

        // $scope.offer3text = details.offer3;

        if (details.showOffer3 == 1) {
          $scope.offer3 = true;
        }

        webfactory.getOffers($scope.clientId).then(function(response) {
          $scope.offers1 = response;
          $scope.offers2 = response;
          $scope.offers3 = response;
          $scope.offers1.splice(0,0,{offerId:0, offerText:"\"New Offer\""});
          $scope.offers2.splice(0,0,{offerId:0, offerText:"\"New Offer\""});
          $scope.offers3.splice(0,0,{offerId:0, offerText:"\"New Offer\""});
          var splice1;
          var splice2;
          var splice3;
          for (var i = 0; i < $scope.offers1.length; i++) {
            if (details.offer1Id == $scope.offers1[i].offerId) {
              console.log("found 1 at " + i);
              $scope.offer1text = $scope.offers1[i];
              offer1Changed = $scope.offers1[i];
              splice1 = i;
            } else if (details.offer2Id == $scope.offers2[i].offerId) {
              console.log("found 2 at " + i);
              $scope.offer2text = $scope.offers2[i];
              offer2Changed = $scope.offers2[i];
              splice2 = i;
            } else if (details.offer3Id == $scope.offers3[i].offerId) {
              console.log("found 3 at " + i);
              $scope.offer3text = $scope.offers3[i];
              offer3Changed = $scope.offers3[i];
              splice3 = i;
            }
          }
          $scope.offers1.splice(splice2,1);
          $scope.offers1.splice(splice3 - 1,1);
          $scope.offers2.splice(splice1,1);
          $scope.offers2.splice(splice3 - 1,1);
          $scope.offers3.splice(splice1,1);
          $scope.offers3.splice(splice2 - 1,1);
          console.log($scope.offers1)
        });

        $scope.foodStyle = details.foodStyle;
        $scope.photo = details.photo;
        $scope.location = details.y + " " + details.x;
        $scope.image = details.photo;
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

    //Check if offer 1 active state has changed
    if ($scope.offer1 !== offer1Active) {
      if ($scope.offer1 == false) {
        //Toggle off
        webfactory.toggleOffer(email, 1, 0).then(function(){
          details();
        });
      } else {
        //Toggle on
        webfactory.toggleOffer(email, 1, 1).then(function(){
          details();
        });;
      }
    }

    //Check if offer 1 offer has changed
    if ($scope.offer1text.offerId !== offer1Changed.offerId) {
      //Check if a new offer is being submitted
      if ($scope.offer1text.offerId == 0) {
        webfactory.insertOffer(email,1,$scope.newOffer1text).then(function(response) {
          webfactory.toggleOffer(email, 1, 1).then(function() {
            details();
          });
        });
      }
      //Replace the current offer with the old offer
      else {
        webfactory.replaceOffer(email,offer1Changed.offerId,$scope.offer1text.offerId,1).then(function() {
          details();
        });
      }
    }

    //Check if offer 2 active state has changed
    if ($scope.offer2 !== offer2Active) {
      if ($scope.offer2 == false) {
        //Toggle off
        webfactory.toggleOffer(email, 2, 0).then(function(){
          details();
        });
      } else {
        //Toggle on
        webfactory.toggleOffer(email, 2, 1).then(function(){
          details();
        });;
      }
    }

    //Check if offer 2 offer has changed
    if ($scope.offer2text.offerId !== offer2Changed.offerId) {
      //Check if a new offer is being submitted
      if ($scope.offer2text.offerId == 0) {
        webfactory.insertOffer(email,2,$scope.newOffer2text).then(function(response) {
          webfactory.toggleOffer(email, 2, 1).then(function() {
            details();
          });
        });
      }
      //Replace the current offer with the old offer
      else {
        webfactory.replaceOffer(email,offer2Changed.offerId,$scope.offer2text.offerId,2).then(function() {
          details();
        });
      }
    }

    //Check if offer 3 active state has changed
    if ($scope.offer3 !== offer3Active) {
      if ($scope.offer3 == false) {
        //Toggle off
        webfactory.toggleOffer(email, 3, 0).then(function(){
          details();
        });
      } else {
        //Toggle on
        webfactory.toggleOffer(email, 3, 1).then(function(){
          details();
        });;
      }
    }

    //Check if offer 3 offer has changed
    if ($scope.offer3text.offerId !== offer3Changed.offerId) {
      //Check if a new offer is being submitted
      if ($scope.offer3text.offerId == 0) {
        webfactory.insertOffer(email,3,$scope.newOffer3text).then(function(response) {
          webfactory.toggleOffer(email, 3, 1).then(function() {
            details();
          });
        });
      }
      //Replace the current offer with the old offer
      else {
        webfactory.replaceOffer(email,offer3Changed.offerId,$scope.offer3text.offerId,3).then(function() {
          details();
        });
      }
    }

  }

  $scope.getLocation = function(val) {
    return webfactory.getLocations(val).then(function(response) {
      return response.data.results.map(function(item){
        console.log(item.formatted_address);
        return item.formatted_address;
      });
    })
  };

})
