<!doctype html>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobKey" %>
<%@ page import="com.google.appengine.api.images.ServingUrlOptions" %>
<%@ page import="com.google.appengine.api.images.ImagesService" %>
<%@ page import="com.google.appengine.api.images.ImagesServiceFactory" %>
<%@ page import="com.google.appengine.api.images.Image" %>
<html lang="en" ng-app="app">
<head>
  <meta charset="utf-8">
  <title>Yapnak</title>

  <!--angularjs-->
  <script
  src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.min.js"></script>
  <script
  src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-cookies.js"></script>
  <script
  src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js"></script>
  <script
  src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.13.3.min.js"></script>
  <script src="app.js"></script>

  <!--bootstrap-->
  <link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="css/index.css">

  <link href="css/index.css" rel="stylesheet">

</head>

<body class="content container" ng-controller="client-controller">
  <%  BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();%>



  <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
  <div class="form-signin center-image">
    <img src="https://placeholdit.imgix.net/~text?txtsize=9&txt=100%C3%97100&w=100&h=100">
  </div>
    <div class="form-signin">
      <label for="exampleInputFile">Logo</label>
      <input type="file" name="image" id="image">
      <p class="help-block">Please keep your image size small and wait a few seconds for it to upload before submitting.</p>
    </div>
    <div class ="form-signin">
    <button type="submit" class="btn btn-default">Update image</button>
    </div>
  </form>

  <div class="form-signin">
    <label
    for="exampleInputEmail1">Restaurant Name</label>
    <input type="text"
    class="form-control" name="name" id="name" placeholder="Test">
  </div>
  <div class="form-signin">  <label for="exampleInputPassword1">Restaurant
    Type</label>
    <input type="text" class="form-control" name="type" id="type"
    placeholder="Food style">
  </div>
  <div class="form-signin">
    <label
    for="exampleInputPassword1">Address</label>
    <input type="text"
    class="form-control" name="address" id="address" placeholder="Geolocation">
  </div>

  <div class="form-signin">
    <label>
      <input type="checkbox" name = "show-offer"
      value="show-offer" ng-model="offer1" ng-click="showOffer()"> Offer one
    </label>
    <hr>
    <div collapse="!offer1">
      <textarea maxlength="250" class="form-control well well-lg" name="deal" id = "deal"
      rows="3" placeholder="Offer"></textarea>
    </div>
  </div>
  <div class="form-signin">
    <label>
      <input type="checkbox" name = "show-offer"
      value="show-offer" ng-model="offer2" ng-click="showOffer()"> Offer two
    </label>
    <hr>
    <div collapse="!offer2">
      <textarea maxlength="250" class="form-control well well-lg" name="deal" id = "deal"
      rows="3" placeholder="Offer"></textarea>
    </div>
  </div>
  <div class="form-signin">
    <label>
      <input type="checkbox" name = "show-offer"
      value="show-offer" ng-model="offer3" ng-click="showOffer()"> Offer three
    </label>
    <hr>
    <div collapse="!offer3">
      <textarea maxlength="250" class="form-control well well-lg" name="deal" id = "deal"
      rows="3" placeholder="Offer"></textarea>
    </div>
  </div>


  <div class="form-signin">
    <button type="submit" class="btn btn-default btn-block">Update
      information
    </button>
  </div>

</body>
</html>
