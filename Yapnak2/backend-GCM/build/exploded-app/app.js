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
  console.log($cookies.get("com.yapnak.email"););
  var email = $cookies.get("com.yapnak.email");
  var details = function() {
    console.log("called"); webfactory.getInfo(email).then(function(details){
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
    });
  }
  details();

  $scope.updateInfo = function() {
    if ($scope.newName == undefined) {

      $scope.newName = $scope.name;
    }

    if ($scope.newFoodStyle == undefined) {
      $scope.newFoodStyle = $scope.foodStyle;
    }

    if ($scope.newLocation !== undefined) {
      webfactory.updateLocation($scope.newLocation,email).then(function(response) {
        $scope.newLocation = "";
      });
    }

    webfactory.updateMainText($scope.newName,$scope.newFoodStyle,email).then(function(response) {
      $scope.newName = "";
      $scope.newFoodStyle = "";
    });
    details();
  }
})
