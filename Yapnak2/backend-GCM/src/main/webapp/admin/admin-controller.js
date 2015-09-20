angular.module('app.controller', [])

.controller('modal-controller', function($scope, $modalInstance) {
  $scope.closeModal= function() {
    $modalInstance.dismiss('cancel');
  };
})

.controller('LoginController', function($scope, webfactory, $modal, $state, detailsfactory){

  $scope.data = {};

  $scope.login = function() {
    if ($scope.data.email === undefined || $scope.data.password === undefined) {
      // If details have not been provided, show an error modal
      $modal.open({
        animation: true,
        templateUrl: 'admin/templates/login-details-missing-modal.html',
        controller: 'modal-controller'
      });
    } else {
      //make API call
      webfactory.login($scope.data).then(function(response) {
        if (response !== -1) {
          //Login success
          console.log("login success");
          detailsfactory.setSession(response.session);
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
      });
    }

  };

})

.controller('ConsoleController', function($scope, webfactory, $modal, detailsfactory, $state, $timeout, fileUpload) {

  if (detailsfactory.getSession() === "") {
    $state.go('login');
  } else {
    console.log("allowed past");
    webfactory.getAllClients().then(function(response) {
      if (response !== -1) {
        $scope.clientList = response.clientList;
      }
    });
  }

  $scope.gotDetails = "";

  $scope.clientList = [];

  $scope.clientData = {};

  $scope.modal = "";

  $scope.photoUrl = "";

  var offer1Changed;
  var offer2Changed;
  var offer3Changed;
  var offer1Active;
  var offer2Active;
  var offer3Active;
  var email;

  $scope.uploadFile = function(){
    var clientId = detailsfactory.getclientId();
    var file = detailsfactory.getFile();
    console.log('file is ' );
    console.dir(file);
    webfactory.getUploadUrl(clientId).then(function(response){
      console.log(response);
      var uploadUrl = response.uploadUrl;
      webfactory.uploadFileToUrl(file, uploadUrl).then(function(response) {
        $scope.retrieveClient();
      }, function(error) {
        $scope.retrieveClient();
      });
    })
  }

  $scope.closeModal= function() {
    $scope.modal.close();
  };

  $scope.retrieveClient = function() {
    //Retrieve client details
    webfactory.retrieveClient($scope.chosenClient.clientId).then(function(response) {
      if (response !== -1) {
        $scope.clientData = response;
        $scope.photoUrl = $scope.clientData.photo;
        detailsfactory.setClientId($scope.clientData.id);
        //Retrieve all client offers
        webfactory.getOffers($scope.chosenClient.clientId).then(function(response) {
          $scope.clientData.offers = response;
          $scope.clientData.offers.splice(0,0,{offerId:0, offerText:"\"New Offer\""});
          for (var i = 0; i < $scope.clientData.offers.length; i++) {
            if ($scope.clientData.offer1Id == $scope.clientData.offers[i].offerId) {
              $scope.clientData.offer1text = $scope.clientData.offers[i];
              offer1Changed = $scope.clientData.offers[i];
            } else if ($scope.clientData.offer2Id == $scope.clientData.offers[i].offerId) {
              $scope.clientData.offer2text = $scope.clientData.offers[i];
              offer2Changed = $scope.clientData.offers[i];
            } else if ($scope.clientData.offer3Id == $scope.clientData.offers[i].offerId) {
              $scope.clientData.offer3text = $scope.clientData.offers[i];
              offer3Changed = $scope.clientData.offers[i];
            }
          }
          $scope.clientData.offers.splice(1,3);
        });

        $scope.gotDetails = "client";
        $scope.clientData.locationText = $scope.clientData.x + " " + $scope.clientData.y;

        $scope.isActive = $scope.clientData.isActive == 1 ? true : false;
        $scope.clientData.offer1Shown = $scope.clientData.showOffer1 == 1 ? true : false;
        $scope.clientData.offer2Shown = $scope.clientData.showOffer2 == 1 ? true : false;
        $scope.clientData.offer3Shown = $scope.clientData.showOffer3 == 1 ? true : false;
        offer1Active = $scope.clientData.offer1Shown;
        offer2Active = $scope.clientData.offer2Shown;
        offer3Active = $scope.clientData.offer3Shown;
        $scope.clientData.newLocation = "";
        $scope.clientData.newName = "";
        $scope.clientData.newFoodStyle = "";
        $scope.clientData.newOffer1text = "";
        $scope.clientData.newOffer2text = "";
        $scope.clientData.newOffer3text = "";
        email = $scope.clientData.email;
      }
    });
  };

  var details = function() {
    var modal = $modal.open({
      animation: true,
      controller: 'modal-controller',
      templateUrl: 'admin/templates/updated-modal.html'
    });
    $timeout(function() {
      console.log("closed");
      modal.close();
    }, 2000);
    $scope.retrieveClient();
  };

  $scope.toggleOn = function() {
    console.log("toggling on");
    $scope.isActive = !$scope.isActive;
    webfactory.toggleClient($scope.clientData.id, 1).then(function() {
      //Modal success?
      $scope.retrieveClient();
    });
  };

  $scope.getEditList = function() {
    return ($scope.editList === "") ? "No Details Changed" : $scope.editList;
  };

  $scope.toggleOff = function() {
    console.log("toggling off");
    $scope.isActive = !$scope.isActive;
    webfactory.toggleClient($scope.clientData.id, 0).then(function() {
      //Modal success?
      $scope.retrieveClient();
    });
  };

  $scope.updateInfo = function() {
    $scope.editList = "";

    if ($scope.clientData.newLocation !== "") {
      $scope.editList += "Client Address to " + $scope.clientData.newLocation + " | ";
    }

    if ($scope.clientData.newFoodStyle !== "") {
      $scope.editList += "Food Style to " + $scope.clientData.newFoodStyle + " | ";
    }

    if ($scope.clientData.newName !== "") {
      $scope.editList += "Client Name to " + $scope.clientData.newName + " | ";
    }

    //Check if offer 1 active state has changed
    if ($scope.clientData.offer1Shown !== offer1Active) {
      if ($scope.clientData.offer1Shown === false) {
        //Toggle off
        $scope.editList += "Offer 1 OFF | ";
      } else {
        //Toggle on
        $scope.editList += "Offer 1 ON | ";
      }
    }

    //Check if offer 1 offer has changed
    if ($scope.clientData.offer1text.offerId !== offer1Changed.offerId) {
      //Check if a new offer is being submitted
      if ($scope.clientData.offer1text.offerId === 0) {
        if ($scope.clientData.newOffer1text !== "") {
          $scope.editList += "A new offer 1:" + $scope.newOffer1text + " | ";
        } else {
          $scope.editList += "A BLANK offer 1 (This will not be updated, please add text or revert back to original offer) | ";
        }
      }
      //Replace the current offer with the old offer
      else {
        $scope.editList += "Replacing offer 1 with: " + $scope.clientData.newOffer1text + " | ";
      }
    }

    //Check if offer 2 active state has changed
    if ($scope.clientData.offer2Shown !== offer2Active) {
      if ($scope.clientData.offer2Shown === false) {
        //Toggle off
        $scope.editList += "Offer 2 OFF | ";
      } else {
        //Toggle on
        $scope.editList += "Offer 2 ON | ";
      }
    }

    //Check if offer 2 offer has changed
    if ($scope.clientData.offer2text.offerId !== offer2Changed.offerId) {
      //Check if a new offer is being submitted
      if ($scope.clientData.offer2text.offerId === 0) {
        if ($scope.clientData.newOffer2text !== "") {
          $scope.editList += "A new offer 2:" + $scope.newOffer2text + " | ";
        } else {
          $scope.editList += "A BLANK offer 2 (This may not be updated, please add text or revert back to original offer) | ";
        }
      }
      //Replace the current offer with the old offer
      else {
        $scope.editList += "Replacing offer 2 with: " + $scope.clientData.newOffer2text + " | ";
      }
    }

    //Check if offer 3 active state has changed
    if ($scope.clientData.offer3Shown !== offer3Active) {
      if ($scope.clientData.offer3Shown === false) {
        //Toggle off
        $scope.editList += "Offer 3 OFF | ";
      } else {
        //Toggle on
        $scope.editList += "Offer 3 ON | ";
      }
    }

    //Check if offer 3 offer has changed
    if ($scope.clientData.offer3text.offerId !== offer3Changed.offerId) {
      //Check if a new offer is being submitted
      if ($scope.clientData.offer3text.offerId === 0) {
        if ($scope.clientData.newOffer3text !== "") {
          $scope.editList += "A new offer 3:" + $scope.newOffer3text + " | ";
        } else {
          $scope.editList += "A BLANK offer 3 (This may not be updated, please add text or revert back to original offer) | ";
        }
      }
      //Replace the current offer with the old offer
      else {
        $scope.editList += "Replacing offer 3 with: " + $scope.clientData.newOffer3text + " | ";
      }
    }
    ////Check for changed details
    $scope.modal = $modal.open({
      animation: true,
      templateUrl: 'admin/templates/confirm-edit-modal.html',
      scope: $scope
    });
  };

  $scope.getLocation = function(val) {
    return webfactory.getLocations(val).then(function(response) {
      return response.data.results.map(function(item){
        console.log(item.formatted_address);
        return item.formatted_address;
      });
    });
  };

  $scope.confirmUpdate = function() {
    $scope.modal.close();
    if((($scope.clientData.offer3text.offerId == $scope.clientData.offer2text.offerId) && $scope.clientData.offer3text.offerId !== 0) || (($scope.clientData.offer3text.offerId == $scope.clientData.offer1text.offerId) && $scope.clientData.offer3text.offerId !== 0) || (($scope.clientData.offer1text.offerId == $scope.clientData.offer2text.offerId) && $scope.clientData.offer2text.offerId !== 0)){
      $modal.open({
        animation: true,
        controller: 'modal-controller',
        templateUrl: 'admin/templates/same-offer-chosen-modal.html'
      });
    } else {
      var counter = 9;
      $scope.editList = "";

      if ($scope.clientData.newLocation !== "") {
        webfactory.updateLocation($scope.clientData.newLocation,email).then(function(response) {
          counter -= 1;
          if (counter === 0) {
            details();
          }
        });
      } else {
        counter -= 1;
      }

      if ($scope.clientData.newFoodStyle !== "") {
        webfactory.updateType($scope.clientData.newFoodStyle,email).then(function(response) {
          counter -= 1;
          if (counter === 0) {
            details();
          }
        });
      } else {
        counter -= 1;
      }

      if ($scope.clientData.newName !== "")  {
        webfactory.updateName($scope.clientData.newName,email).then(function(response) {
          counter -= 1;
          if (counter === 0) {
            details();
          }
        });
      } else {
        counter -=1 ;
      }

      //Check if offer 1 active state has changed
      if ($scope.clientData.offer1Shown !== offer1Active) {
        if ($scope.clientData.offer1Shown === false) {
          //Toggle off
          webfactory.toggleOffer(email, 1, 0).then(function(){
            counter -= 1;
            if (counter === 0) {
              details();
            }
          });
        } else {
          //Toggle on
          webfactory.toggleOffer(email, 1, 1).then(function(){
            counter -= 1;
            if (counter === 0) {
              details(1);
            }
          });
        }
      } else {
        counter -= 1;
      }

      //Check if offer 1 offer has changed
      if ($scope.clientData.offer1text.offerId !== offer1Changed.offerId) {
        //Check if a new offer is being submitted
        if ($scope.clientData.offer1text.offerId === 0) {
          if ($scope.clientData.newOffer1text !== "") {
            webfactory.insertOffer(email,1,$scope.clientData.newOffer1text).then(function(response) {
              webfactory.toggleOffer(email, 1, 1).then(function() {
                counter -= 1;
                if (counter === 0) {
                  details();
                }
              });
            });
          } else {
            $modal.open({
              animation: true,
              controller: 'modal-controller',
              templateUrl: 'admin/templates/blank-offer-modal.html'
            });
          }
        }
        //Replace the current offer with the old offer
        else {
          webfactory.replaceOffer(email,offer1Changed.offerId,$scope.clientData.offer1text.offerId,1).then(function() {
            counter -= 1;
            if (counter === 0) {
              details();
            }
          });
        }
      } else {
        counter -= 1;
      }

      //Check if offer 2 active state has changed
      if ($scope.clientData.offer2Shown !== offer2Active)  {
        if ($scope.clientData.offer2Shown === false) {
          //Toggle off
          webfactory.toggleOffer(email, 2, 0).then(function(){
            counter -= 1;
            if (counter === 0) {
              details();
            }
          });
        } else {
          //Toggle on
          webfactory.toggleOffer(email, 2, 1).then(function(){
            counter -= 1;
            if (counter === 0) {
              details();
            }
          });
        }
      } else {
        counter -= 1;
      }

      //Check if offer 2 offer has changed
      if ($scope.clientData.offer2text.offerId !== offer2Changed.offerId) {
        //Check if a new offer is being submitted
        if ($scope.clientData.offer2text.offerId === 0) {
          if ($scope.clientData.newOffer2text !== "") {
            webfactory.insertOffer(email,2,$scope.clientData.newOffer2text).then(function(response) {
              webfactory.toggleOffer(email, 2, 1).then(function() {
                counter -= 1;
                if (counter === 0) {
                  details();
                }
              });
            });
          } else {
            $modal.open({
              animation: true,
              controller: 'modal-controller',
              templateUrl: 'admin/templates/blank-offer-modal.html'
            });
          }
        }
        //Replace the current offer with the old offer
        else {
          webfactory.replaceOffer(email,offer2Changed.offerId,$scope.clientData.offer2text.offerId,2).then(function() {
            counter -= 1;
            if (counter === 0) {
              details();
            }
          });
        }
      } else {
        counter -= 1;
      }

      //Check if offer 3 active state has changed
      if ($scope.clientData.offer3Shown !== offer3Active) {
        if ($scope.clientData.offer3Shown === false) {
          //Toggle off
          webfactory.toggleOffer(email, 3, 0).then(function(){
            counter -= 1;
            if (counter === 0) {
              details();
            }
          });
        } else {
          //Toggle on
          webfactory.toggleOffer(email, 3, 1).then(function(){
            counter -= 1;
            if (counter === 0) {
              details();
            }
          });
        }
      } else {
        counter -= 1;
      }

      //Check if offer 3 offer has changed
      if ($scope.clientData.offer3text.offerId !== offer3Changed.offerId) {
        //Check if a new offer is being submitted
        if ($scope.clientData.offer3text.offerId === 0) {
          if ($scope.clientData.newOffer3text !== "") {
            webfactory.insertOffer(email,3,$scope.newOffer3text).then(function(response) {
              webfactory.toggleOffer(email, 3, 1).then(function() {
                counter -= 1;
                if (counter === 0) {
                  details();
                }
              });
            });
          } else {
            $modal.open({
              animation: true,
              controller: 'modal-controller',
              templateUrl: 'admin/templates/blank-offer-modal.html'
            });
          }
        }
        //Replace the current offer with the old offer
        else {
          webfactory.replaceOffer(email,offer3Changed.offerId,$scope.clientData.offer3text.offerId,3).then(function() {
            counter -= 1;
            if (counter === 0) {
              details();
            }
          });
        }
      } else {
        counter -= 1;
      }
    } //if/else
  }; //confirm update

}) //console controller

.controller('myCtrl', function($scope, fileUpload, detailsfactory, webfactory, $timeout){

  $scope.uploadFile = function(){
    var clientId = detailsfactory.getclientId();
    var file = $scope.myFile;
    console.log('file is ' );
    console.dir(file);
    webfactory.getUploadUrl(clientId).then(function(response){
      console.log(response);
      var uploadUrl = response.uploadUrl;
      fileUpload.uploadFileToUrl(file, uploadUrl);
    })
  }

  $scope.addFile = function() {
    $timeout(function() {
      var file = $scope.myFile;
      detailsfactory.setFile(file);
      console.log("set file ");
      console.dir(file);
    }, 100);
  }

})

.service('fileUpload', ['$http', function ($http) {
  this.uploadFileToUrl = function(file, uploadUrl){
    var fd = new FormData();
    fd.append('image', file);
    $http.post(uploadUrl, fd, {
      transformRequest: angular.identity,
      headers: {'Content-Type': undefined},
      options: {
        withCredentials: true
      },
    })
    .success(function(){
    })
    .error(function(){
    });
  }
}])

.directive('fileModel', ['$parse', function ($parse) {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
      var model = $parse(attrs.fileModel);
      var modelSetter = model.assign;

      element.bind('change', function(){
        scope.$apply(function(){
          modelSetter(scope, element[0].files[0]);
        });
      });
    }
  };
}])
