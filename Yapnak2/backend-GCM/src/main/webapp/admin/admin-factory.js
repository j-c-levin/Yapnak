angular.module('app.factories', [])

.factory('webfactory', ['$http', function($http){
  var result = {};
  
  result.login = function(data) {
    var req = {
      method: 'POST',
      // url: 'https://yapnak-app.appspot.com/_ah/api/sQLEntityApi/v1/forgotLogin',
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
  }
  
  return result;
}])
