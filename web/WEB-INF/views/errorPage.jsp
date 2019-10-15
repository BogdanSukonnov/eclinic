<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<jsp:include page="common/head.jsp"/>
<body>
<div id="ecl-main-wrapper">
    <jsp:include page="common/header.jsp"/>
    <div class="ecl-error-content $
                 {<c:choose>
                  <c:when test="${isAccessDenied}">
                    ecl-access-denied-content
                  </c:when>
                  <c:otherwise>
                  </c:otherwise>
                </c:choose>}">
        <h2>${headerText}</h2>
        <p>${exceptionObj}</p>
    </div>
</div>

<jsp:include page="common/scripts.jsp"/>
</body>
</html>
