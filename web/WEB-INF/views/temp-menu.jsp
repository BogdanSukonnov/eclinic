<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
  <jsp:include page="head.jsp"/>
  <body>
  <jsp:include page="header.jsp"/>

    <h1>eClinic</h1>
    <p>temp menu</p>
    <ul>
      <li><a href="${pageContext.request.contextPath}/doctor/patients">patients</a></li>
      <li><a href="${pageContext.request.contextPath}/admin/addUser">new user</a></li>
      <li><a href="${pageContext.request.contextPath}/login/sign-in">sign in</a></li>
    </ul>

    <jsp:include page="scripts.jsp"/>
  </body>
</html>
