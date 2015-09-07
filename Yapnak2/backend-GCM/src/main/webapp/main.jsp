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
  <script src="modules/factories.js"></script>
  <!--bootstrap-->
  <link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="css/index.css">

  <link href="css/index.css" rel="stylesheet">

</head>

<body class="content container" ng-controller="client-controller">
  <%  BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();%>

  <!-- <a class="form-signin" href="redeem.html">Go to input page</a>  -->

  <!-- TODO:Switch in production! -->

    <a class="form-signin" href="/redeem">Go to input page</a>

  <form action="<%= blobstoreService.createUploadUrl("/upload2") %>" method="post" enctype="multipart/form-data" ng-click="uploadImage">
    <div class="form-signin center-image">
      <img height="100" ng-src="{{image}}">
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
    class="form-control" name="name" ng-model="newName" id="name" placeholder="{{name}}">
  </div>
  <div class="form-signin">
    <label for="exampleInputPassword1">Restaurant Type</label>
    <input type="text" class="form-control" ng-model="newFoodStyle" name="type" id="type"
    placeholder="{{foodStyle}}">
  </div>
  <div class="form-signin">
    <label
    for="exampleInputPassword1">Address</label>
    <input type="text"
    class="form-control" ng-model="newLocation" name="address" id="address" placeholder="{{location}}">
  </div>

  <div class="form-signin">
    <label>
      <input type="checkbox" name = "show-offer"
      value="show-offer" ng-model="offer1" ng-click="showOffer()"> Offer one
    </label>
    <hr>
    <div collapse="!offer1">
      <!-- <textarea maxlength="250" class="form-control well well-lg" name="deal" id = "deal"
      rows="3" ng-model="newOffer1text" placeholder="{{offer1text}}"></textarea> -->
      <input type="text"
      class="form-control" maxlength="160" ng-model="newOffer1text" name="deal" id="deal" placeholder="{{offer1text}}">
    </div>
  </div>

  <div class="form-signin">
    <label>
      <input type="checkbox" name = "show-offer"
      value="show-offer" ng-model="offer2" ng-click="showOffer()"> Offer two
    </label>
    <hr>
    <div collapse="!offer2">
      <!-- <textarea maxlength="250" class="form-control well well-lg" name="deal" id = "deal"
      rows="3" ng-model="newOffer2text" placeholder="{{offer2text}}"></textarea> -->
      <input type="text"
      class="form-control" maxlength="160" ng-model="newOffer2text" name="deal" id="deal" placeholder="{{offer2text}}">
    </div>
  </div>

  <div class="form-signin">
    <label>
      <input type="checkbox" name = "show-offer"
      value="show-offer" ng-model="offer3" ng-click="showOffer()" DISABLED> Offer three
    </label>
    <hr>
    <div collapse="!offer3">
      <!-- <textarea maxlength="250" class="form-control well well-lg" name="deal" id = "deal"
      rows="3" ng-model="newOffer3text" placeholder="{{offer3text}}"></textarea> -->
      <input type="text"
      class="form-control" maxlength="160" ng-model="newOffer3text" name="deal" id="deal" placeholder="{{offer3text}}">
    </div>
  </div>


  <div class="form-signin">
    <button class="btn btn-default btn-block" ng-click="updateInfo()">Update
      information
    </button>
  </div>

</body>
</html>