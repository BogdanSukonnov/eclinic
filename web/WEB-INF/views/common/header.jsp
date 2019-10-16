<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header id="ecl-main-navbar" class="navbar navbar-expand-lg navbar-dark justify-content-between">
    <a class="navbar-brand" href="/">
        <img src="<c:url value="/static/images/logo.svg" />" alt="logo" height="32">
        <spring:eval expression="@environment.getProperty('company.name')" />
    </a>
    <sec:authorize access="isAuthenticated()">
        <div class="nav-item dropdown navbar-nav">
            <a class="nav-link dropdown-toggle" href="" id="UserDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <sec:authentication property="principal.fullName" />
            </a>
            <div class="dropdown-menu" aria-labelledby="UserDropdown">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/login/sign-out">Logout</a>
            </div>
        </div>
    </sec:authorize>
</header>
