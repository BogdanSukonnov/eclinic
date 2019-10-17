<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div class="error-content $
             {<c:choose>
              <c:when test="${isAccessDenied}">
                access-denied-content
              </c:when>
              <c:otherwise>
              </c:otherwise>
            </c:choose>}">
    <h2>${headerText}</h2>
    <p>${exceptionObj}</p>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>
