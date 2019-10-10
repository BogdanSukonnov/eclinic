<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>welcome</title>
  </head>
  <body>
  <h1>eClinic</h1>
  <p>Welcome to eClinic!</p>
  <ul>
    <li><a href="${pageContext.request.contextPath}/doctor/patients">patients</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/addUser">new user</a></li>
    <li><a href="${pageContext.request.contextPath}/login/sign-in">sign in</a></li>
  </ul>
  </body>
</html>
