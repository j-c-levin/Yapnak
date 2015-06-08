<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.google.appengine.api.utils.SystemProperty" %>

<%
String email="",password="";
if(request.getParameter("email")!=null){
    email=request.getParameter("email");
}
if(request.getParameter("password")!=null){
    password=request.getParameter("password");
}
%>

<%
Connection con=null;
PreparedStatement pst=null;
ResultSet re=null;
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
                con = DriverManager.getConnection("jdbc:google:mysql://yapnak-app:yapnak-main/yapnak_main?user=root");
            } else {
                // Local MySQL instance to use during development.
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");
            }
//String sql="select clientID, password FROM client WHERE email = ?";
String sql="select * FROM user";
try{
pst=con.prepareStatement(sql);
//pst.setString(1,email);
re=pst.executeQuery();
if(re.next()){
    do {out.println(re.getString("userID"));}
    while (re.next());
}else{
    out.println("LOGIN FAILED!!!");
}
} catch(Exception e){
    out.println("ERROR CONNECTING");
}
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <jsp:useBean id="actualsession" class="com.yapnak.website.session" scope="session"/>
        <jsp:setProperty name="actualsession" property="email" value="<%=email%>"/>
        <jsp:setProperty name="actualsession" property="password" value="<%=password%>"/>
  
  <%--   
        <jsp:getProperty name="actualsession" property="email"/><br/>
        <jsp:getProperty name="actualsession" property="password"/><br/>
  --%>   
      
      
    </body>

</html>