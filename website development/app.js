angular.module('app', ['ngCookies','ui.bootstrap','ngAnimate'])

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

  result.getInfo = function(email) {
    var req = {
      method: 'GET',
      url: 'https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/getClientInfo?email='.concat(email)
    }
    return $http(req).then(function (response) {
      if (response.data.status == "True") {
        console.log("Got information");
        console.log(response.data);
        return response.data;
      } else {
        console.log("Failed");
        console.log(response);
      }
    },function (error) {
      console.log("failed");
      console.log(error);
      return null;
    });
  }

  result.updateMainText = function(name,type,email) {
    var req = {
      method: 'POST',
      url: 'http://localhost:8080/_ah/api/sQLEntityApi/v1/updateClientInfo?email='.concat(email).concat("&name=").concat(name).concat("&type=").concat(type)
    }
    return $http(req).then(function (response) {
      if (response.data.status == "True") {
        console.log("Successfully updated main information");
      } else {
        console.log("Failed");
        console.log(response);
      }
    },function (error) {
      console.log("failed");
      console.log(error);
    });
  };

  result.updateLocation = function(address,email) {
    var req = {
      method: 'POST',
      url: 'http://localhost:8080/_ah/api/sQLEntityApi/v1/updateClientLocation?email='.concat(email).concat("&address=").concat(address)
    }
    return $http(req).then(function(response){
      if (response.data.status == "True") {
        console.log("successfully updated location");
        console.log(response);
      } else {
        console.log("location update failed");
        console.log(response);
      }
    }, function(error) {
      console.log("Location update went wrong somewhere");
      console.log(error);
    })
  };

  result.toggleOffer = function(email,offer,value) {
    var req = {
      method: 'POST',
      url: 'http://localhost:8080/_ah/api/sQLEntityApi/v1/toggleOffer?email='.concat(email).concat("&offer=").concat(offer).concat("&value=").concat(value)
    }
    return $http(req).then(function(response){
      if (response.data.status == "True") {
        console.log("successfully updated offer status");
        console.log(response);
      } else {
        console.log("failed to update offer status");
        console.log(response);
      }
    }, function(error) {
      console.log("Offer toggle update went wrong somewhere");
      console.log(error);
    })
  };

  result.updateOffer = function(email,offer,text) {
    var req = {
      method: 'POST',
      url: 'http://localhost:8080/_ah/api/sQLEntityApi/v1/updateOffer?email='.concat(email).concat("&offer=").concat(offer).concat("&text=").concat(text)
    }
    return $http(req).then(function(response){
      if (response.data.status == "True") {
        console.log("successfully updated offer text");
        console.log(response);
      } else {
        console.log("failed to update offer text");
        console.log(response);
      }
    }, function(error) {
      console.log("Offer text update went wrong somewhere");
      console.log(error);
    })
  };

  result.insertOffer = function(email,offer,text) {
    var req = {
      method: 'POST',
      url: 'http://localhost:8080/_ah/api/sQLEntityApi/v1/insertOffer?email='.concat(email).concat("&offer=").concat(offer).concat("&text=").concat(text)
    }
    return $http(req).then(function(response){
      if (response.data.status == "True") {
        console.log("successfully updated offer text");
        console.log(response);
      } else {
        console.log("failed to update offer text");
        console.log(response);
      }
    }, function(error) {
      console.log("Offer text update went wrong somewhere");
      console.log(error);
    })
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

.controller('client-controller', function($scope, webfactory, $cookies){

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
      $scope.name = details.name;
      if (details.showOffer1 == 1) {
        $scope.offer1text = details.offer1;
        $scope.offer1 = true;
      }
      if (details.showOffer2 == 1) {
        $scope.offer2text = details.offer2;
        $scope.offer2 = true;
      }
      if (details.showOffer3 == 1) {
        $scope.offer3text = details.offer3;
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
    });
  }
  details();

  $scope.updateInfo = function() {

    if ($scope.newLocation !== undefined) {
      webfactory.updateLocation($scope.newLocation,email).then(function(response) {
        details();
        $scope.newLocation = "";
      });
    }

    if ($scope.newFoodStyle !== undefined && $scope.newName !== undefined) {

      if ($scope.newName == undefined) {
        $scope.newName = $scope.name;
      }

      if ($scope.newFoodStyle == undefined) {
        $scope.newFoodStyle = $scope.foodStyle;
      }

      webfactory.updateMainText($scope.newName,$scope.newFoodStyle,email).then(function(response) {
        details();
        $scope.newName = "";
        $scope.newFoodStyle = "";
      });
    }

    if ($scope.offer1 !== offer1Active) {
      console.log("offer 1 toggled");
      if ($scope.offer1 == false) {
        webfactory.toggleOffer(email, 1, 0);
      } else {
        //check if offer has been placed and then create new offer
        if ($scope.newOffer1text !== undefined) {
          webfactory.toggleOffer(email, 1, 1);//use .then
          webfactory.insertOffer(email,1,$scope.newOffer1text).then(function(response){
            details();
            $scope.newOffer1text = "";
          });
        }
      }
    } else {
      if ($scope.newOffer1text !== offer1Changed && $scope.newOffer1text !== "" && $scope.newOffer1text !== undefined) {
        console.log("offer 1 changed");
        webfactory.updateOffer(email,1,$scope.newOffer1text).then(function(response){
          details();
          $scope.newOffer1text = "";
        })
      }
    }

    if ($scope.offer2 !== offer2Active) {
      if ($scope.offer2 == false) {
        webfactory.toggleOffer(email, 2, 0);
      } else {
        //check if offer has been placed and then create new offer
        if ($scope.newOffer2text !== undefined) {
          webfactory.toggleOffer(email, 2, 1);
          webfactory.insertOffer(email,2,$scope.newOffer2text).then(function(response){
            details();
            $scope.newOffer1text = "";
          });
        }
      }
    } else {
      if ($scope.newOffer2text !== offer2Changed  && $scope.newOffer2text !== "" && $scope.newOffer2text !== undefined) {
        webfactory.updateOffer(email,2,$scope.newOffer2text).then(function(response){
          details();
          $scope.newOffer2text = "";
        })
      }
    }

    if ($scope.offer3 !== offer3Active) {
      if ($scope.offer3 == false) {
        webfactory.toggleOffer(email, 3, 0);
      } else {
        //check if offer has been placed and then create new offer
        if ($scope.newOffer3text !== undefined) {
          webfactory.toggleOffer(email, 3, 1);
          webfactory.insertOffer(email,3,$scope.newOffer3text).then(function(response){
            details();
            $scope.newOffer1text = "";
          });
        }
      }
    } else {
      if ($scope.newOffer3text !== offer3Changed  && $scope.newOffer3text !== "" && $scope.newOffer3text !== undefined) {
        webfactory.updateOffer(email,3,$scope.newOffer3text).then(function(response){
          details();
          $scope.newOffer3text = "";
        })
      }
    }

  }

})
