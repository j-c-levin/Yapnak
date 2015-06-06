<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.google.appengine.api.utils.SystemProperty" %>

<html>
  <body>

<p><strong>Sign the guestbook!</strong></p>
<form action="/sign" method="post">
    <div>First Name: <input type="text" name="fname"></input></div>
    <div>Message:
    <br /><textarea name="content" rows="3" cols="60"></textarea>
    </div>
    <div><input type="submit" value="Post Greeting" /></div>
    <input type="hidden" name="guestbookName" />
  </form>
  </body>
</html>