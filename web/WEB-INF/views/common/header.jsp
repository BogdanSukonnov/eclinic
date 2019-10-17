<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<header class="main-header navbar navbar-expand-lg navbar-dark justify-content-between">
    <a class="navbar-brand" href="/">
        <img src="<c:url value="/static/images/logo.svg" />" alt="logo" height="32">
        <spring:eval expression="@environment.getProperty('company.name')" />
    </a>
</header>
