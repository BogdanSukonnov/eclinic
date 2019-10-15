<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- flavicon -->
    <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/static/images/favicon-package/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/static/images/favicon-package/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/static/images/favicon-package/favicon-16x16.png">
    <link rel="manifest" href="${pageContext.request.contextPath}/static/images/favicon-package/site.webmanifest">
    <link rel="mask-icon" href="${pageContext.request.contextPath}/static/images/favicon-package/safari-pinned-tab.svg" color="#5bbad5">
    <meta name="msapplication-TileColor" content="#da532c">
    <meta name="theme-color" content="#ffffff">

    <!-- app css       -->
    <link rel="stylesheet" href="<c:url value="/static/css/eclinic.css" />">

    <title>
        <spring:eval expression="@environment.getProperty('company.name')" />
    </title>
</head>