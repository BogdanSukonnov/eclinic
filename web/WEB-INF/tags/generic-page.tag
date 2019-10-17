<%@tag description="Page structure tag" pageEncoding="UTF-8"%>
<%@attribute name="content" fragment="true" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<jsp:include page="../views/common/head.jsp"/>
<body>
<div class="main-wrapper">
    <jsp:include page="../views/common/header.jsp"/>
<%--    <sec:authorize access="isAuthenticated()">--%>
        <jsp:include page="../views/common/nav.jsp"/>
<%--    </sec:authorize>--%>
    <jsp:invoke fragment="content"/>
</div>

<jsp:include page="../views/common/scripts.jsp"/>
</body>
</html>
