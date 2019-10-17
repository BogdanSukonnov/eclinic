<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <h1>Add user</h1>
    <form action="${pageContext.request.contextPath}/admin/addUser" method="post">
        <label>login:
            <input type="text" name="username"/>
        </label><br>
        <label>password:
            <input type="text" name="password"/>
        </label><br>
        <input type="submit" />
    </form>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>
