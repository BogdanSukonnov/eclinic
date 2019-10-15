<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<header id="ecl-main-navbar" class="navbar navbar-dark">
    <a class="navbar-brand" href="#">
        <img src="<c:url value="/static/images/logo.svg" />" alt="logo" height="32">
        <spring:eval expression="@environment.getProperty('company.name')" />
    </a>
</header>
