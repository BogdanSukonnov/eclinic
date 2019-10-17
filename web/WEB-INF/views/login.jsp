<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<jsp:include page="common/head.jsp"/>
<body>
<div class="ecl-main-wrapper">
    <jsp:include page="common/header.jsp"/>
    <nav class="ecl-main-nav"></nav>
    <div class="ecl-login-content ecl-content">
        <form class="ecl-login-form" action="${pageContext.request.contextPath}/login/doLogin" method="post">
            <fieldset>
                <legend align="center">Sign In</legend>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        Username or password didn't match!
                        <br/>
                    </div>
                </c:if>
                <div class="form-group">
                    <input class="form-control" placeholder="User Name"
                           name='username' type="text">
                </div>
                <div class="form-group">
                    <input class="form-control" placeholder="Password"
                           name='password' type="password" value="">
                </div>
                <input class="ecl-login-submit btn" type="submit" value="Login">
            </fieldset>
        </form>
    </div>
</div>

<jsp:include page="common/scripts.jsp"/>
</body>
</html>
