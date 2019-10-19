<%@tag description="Page structure tag" pageEncoding="UTF-8"%>
<%@attribute name="content" fragment="true" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<c:import url="${pageContext.request.contextPath}/WEB-INF/views/common/head.jsp" />
<body>
<div class="main-wrapper">
    <c:import url="${pageContext.request.contextPath}/WEB-INF/views/common/header.jsp" />
    <c:import url="${pageContext.request.contextPath}/WEB-INF/views/common/nav.jsp" />
    <jsp:invoke fragment="content"/>
</div>
<c:import url="${pageContext.request.contextPath}/WEB-INF/views/common/scripts.jsp" />
</body>
</html>
