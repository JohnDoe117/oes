<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.augmentum.oes.model.User" %>
<%@page import="com.augmentum.oes.Oes"%>
<!DOCTYPE html >
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Welcome</title>
  </head>
  <script>
      function logout() {
          var LoginObj=document.getElementById("logoutForm");
          LoginObj.submit();
      }
  </script>
  <body>
    <%
    User user = (User) session.getAttribute(Oes.USER); 
    if (user != null) {
    %>
        welcome<%= user.getUserName()%>
    <%
    } else {
        out.print("logout");
    }
    %>
    <br/>
    <form action="LogoutServlet.action" method="POST" id="logoutForm">
    <input type="button" value="Logout" onclick="logout()">
    </form>
  </body>
</html>