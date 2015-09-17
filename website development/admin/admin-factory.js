angular.module('app.factories', [])

.factory('detailsfactory', [function() {
  var result = {};

  var session = "";

  result.setSession = function(details) {
    session = details;
  }

  result.getSession = function() {
    return session;
  }

  return result;
}])

.factory('webfactory', ['$http','detailsfactory', function($http,detailsfactory){
  var result = {};

  result.login = function(data) {
    var req = {
      method: 'POST',
      // url: 'https://yapnak-app.appspot.com/_ah/api/adminApi/v1/adminLogin?email='.concat(data.email).concat("&password=").concat(data.password)
      url: 'http://localhost:8080/_ah/api/adminApi/v1/adminLogin?email='.concat(data.email).concat("&password=").concat(data.password)
    }
    return $http(req).then(function(response){
      if (response.data.status == "True") {
        console.log("admin login success");
        console.log(response);
        return response.data;
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
    console.log(req);
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
    var req = {
      method: 'GET',
      // url: 'https://yapnak-app.appspot.com/_ah/api/adminApi/v1/getClientInfo?clientId='.concat(clientId)
      url: 'http://localhost:8080/_ah/api/adminApi/v1/getClientInfo?clientId='.concat(clientId)
    }
    return $http(req).then(function(response) {
      if (response.data.status == "True") {
        console.log("Retrieved client data");
        console.log(response);
        return response.data;
      } else {
        console.log("FAILED retrieving client data");
        console.log(response);
        return -1
      }
    }, function(error){
      console.log("REALLY FAILED retrieving client data");
      console.log(error);
      return -1
    });
  };

  result.toggleClient = function(clientId,value) {
    var req = {
      method: 'POST',
      // url: 'https://yapnak-app.appspot.com/_ah/api/adminApi/v1/toggleClient?clientId='.concat(clientId).concat("&value=").concat(value).concat("&session=").concat(detailsfactory.getSession())
      url: 'http://localhost:8080/_ah/api/adminApi/v1/toggleClient?clientId='.concat(clientId).concat("&value=").concat(value).concat("&session=").concat(detailsfactory.getSession())
    }
    return $http(req).then(function(response) {
      if (response.data.status == "True") {
        console.log("Toggled client success");
        console.log(response);
        return response.data;
      } else {
        console.log("FAILED Toggled client");
        console.log(response);
        return -1
      }
    }, function(error){
      console.log("REALLY FAILED Toggled client");
      console.log(error);
      return -1
    });

  }

  return result;
}])
