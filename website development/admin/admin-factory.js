angular.module('app.factories', [])

.factory('detailsfactory', [function() {
  var result = {};

  result.setDetails = function(details) {
    result = details;
  }

  result.getDetails = function() {
    return result;
  }

  return result;
}])

.factory('webfactory', ['$http', '$q', function($http, $q){
  var result = {};

  result.login = function(data) {
    var req = {
      method: 'POST',
      // url: 'https://yapnak-app.appspot.com/_ah/api/adminApi/v1/adminLogin?email='.concat(data.email).concat("&password=").concat(data.password),
      url: 'http://localhost:8080/_ah/api/adminApi/v1/adminLogin?email='.concat(data.email).concat("&password=").concat(data.password)
    }
    return $http(req).then(function(response){
      if (response.data.status == "True") {
        console.log("admin login success");
        console.log(response);
        return 1;
      } else {
        console.log("admin login FAILED");
        console.log(response);
        return -1;
      }
    }, function(error){
      console.log("admin login FAILED");
      console.log(error);
      return -1;
    })
  };

  result.getAllClients = function() {
    var req = {
      method: 'GET',
      // url: 'https://yapnak-app.appspot.com/_ah/api/adminApi/v1/getAllClients'
      url: 'http://localhost:8080/_ah/api/adminApi/v1/getAllClients'
    }
    return $http(req).then(function(response) {
      if (response.data.status == "True") {
        console.log("Retrieved clients");
        console.log(response);
        return response.data;
      } else {
        console.log("FAILED retrieving clients");
        console.log(response);
        return -1
      }
    }, function(error){
      console.log("REALLY FAILED retrieving clients");
      console.log(error);
      return -1
    })
  };

  result.retrieveClient = function(clientId) {
    // var req = {
    //   method: 'POST',
    //   // url: 'https://yapnak-app.appspot.com/_ah/api/adminApi/v1/adminLogin?email='.concat(data.email).concat("&password=").concat(data.password),
    //   url: 'http://localhost:8080/_ah/api/adminApi/v1/adminLogin?email='.concat(data.email).concat("&password=").concat(data.password)
    // }
    // return $http(req).then(function(response) {
    //   if (response.data.status == "True") {
    //     console.log("Retrieved client data");
    //     console.log(response);
    //     return response.data;
    //   } else {
    //     console.log("FAILED retrieving client data");
    //     console.log(response);
    //     return -1
    //   }
    // }, function(error){
    //   console.log("REALLY FAILED retrieving client data");
    //   console.log(error);
    //   return -1
    // })
    var response = $q.defer();
    response.resolve(1);
    return response.promise;
  };

  return result;
}])
