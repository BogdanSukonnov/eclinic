<%@tag description="Page structure tag" pageEncoding="UTF-8"%>
    <%@attribute name="content" fragment="true" %>
    <%@attribute name="pageScripts" fragment="true" %>
    <%@attribute name="pageStyles" fragment="true" %>
    <%@attribute name="mainWrapperId"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- favicon       -->
        <c:import url="${pageContext.request.contextPath}/WEB-INF/views/common/favicon.jsp" />

        <!-- app css       -->
        <link rel="stylesheet" href="<c:url value="/static/css/eclinic.css" />">
        <!-- inject page's CSS       -->
        <jsp:invoke fragment="pageStyles"/>

        <title>
            <spring:eval expression="@environment.getProperty('company.name')" />
        </title>
    </head>

    <body>
        <div class="main-wrapper" id="${mainWrapperId}">
            <c:import url="${pageContext.request.contextPath}/WEB-INF/views/common/header.jsp" />
            <c:import url="${pageContext.request.contextPath}/WEB-INF/views/common/nav.jsp" />
            <div id="main-content">
                <jsp:invoke fragment="content"/>
            </div>
        </div>
        <c:import url="${pageContext.request.contextPath}/WEB-INF/views/common/scripts.jsp" />
        <%--    inject scripts    --%>
        <jsp:invoke fragment="pageScripts"/>
    </body>
</html>
