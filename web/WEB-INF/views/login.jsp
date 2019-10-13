<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <jsp:include page="head.jsp"/>
    <body>
        <jsp:include page="header.jsp"/>

        <h1>Login</h1>
        <div class="container">
            <form action="${pageContext.request.contextPath}/login/doLogin" method="post">
                <fieldset>
                    <legend>Please sign in</legend>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            Username or password didn't match!
                            <br/>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <input class="form:input-large" placeholder="User Name"
                        name='username' type="text">
                    </div>
                    <div class="form-group">
                        <input class=" form:input-large" placeholder="Password"
                        name='password' type="password" value="">
                    </div>
                    <input class="btn" type="submit" value="Login">
                </fieldset>
            </form>
        </div>

        <jsp:include page="scripts.jsp"/>
    </body>
</html>
