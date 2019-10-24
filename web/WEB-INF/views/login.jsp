<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div class="main-login-content main-content">
    <form class="login-form" action="${pageContext.request.contextPath}/login/doLogin" method="post">
        <fieldset>
            <legend>Sign In</legend>
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
            <input class="login-submit btn" type="submit" value="Login">
        </fieldset>
    </form>
</div>

<%--Common part start--%>
</jsp:attribute>
    <jsp:attribute name="mainWrapperId">login-main-wrapper</jsp:attribute>
</t:generic-page>
<%--Common part end--%>