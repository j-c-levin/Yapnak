<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.google.appengine.api.utils.SystemProperty" %>

  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="icon" href="../../favicon.ico">

    <title>Yapnak - your home for great value lunches</title>

    <!-- Bootstrap core CSS -->
    <link href="stylesheets/bootstrap.css" rel="stylesheet">
        <style type="text/css">
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .checkbox {
  margin-bottom: 10px;
}
.form-signin .checkbox {
  font-weight: normal;
}
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}
.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}
.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

        </style>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

  <%
  String email = null;

  Connection connection = null;

                Class.forName("com.mysql.jdbc.GoogleDriver");
                connection = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            String sql = "SELECT clientName,clientFoodStyle,clientOffer,clientPhoto FROM client WHERE email = ?";
                            PreparedStatement stmt = connection.prepareStatement(sql);
                            stmt.setString(1, (String)request.getSession().getAttribute("email"));
                            ResultSet rs = null;
                            rs = stmt.executeQuery();
            rs.next();
  %>

    <form action="/update" method="post">
  <div class="form-signin">
    <label for="exampleInputEmail1">Restaurant Name</label>
    <input type="text" class="form-control" id="name" placeholder="<%= rs.getString("clientName") %>">
  </div>
  <div class="form-signin">
    <label for="exampleInputPassword1">Restaurant Type</label>
    <input type="text" class="form-control" id="type" placeholder="<%= rs.getString("clientFoodStyle") %>">
  </div>
    <div class="form-signin">
    <label for="exampleInputPassword1">Address</label>
    <input type="text" class="form-control" id="address" placeholder="their address here">
  </div>
    <div class="form-signin">
    <label for="exampleInputPassword1">Deal text</label><p>
	<textarea class="form-control" id = "deal" rows="3" placeholder="<%= rs.getString("clientOffer") %>"></textarea>
  </div>
  <div class="form-signin">
    <label for="exampleInputFile">Logo</label>
    <input type="file" id="exampleInputFile">
    <p class="help-block">Example block-level help text here.</p>
  </div>
  <div class="form-signin">
    <label>
      <input type="checkbox"> Update
    </label>
  </div>
  <div class ="form-signin">
  <button type="submit" class="btn btn-default">Submit</button>
  </div>
</form>

  </body>
</html>