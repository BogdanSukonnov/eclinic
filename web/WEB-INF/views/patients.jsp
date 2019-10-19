<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>Patients</h1>
        <nav class="navbar navbar-light bg-light">
            <form class="form-inline">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
            <a class="btn btn-outline-success my-2 my-sm-0" href="">
                New patient
            </a>
        </nav>
    </div>
    <div class="container-fluid">
        <table class="table">
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Status</th>
                <th scope="col">Insurance</th>
            </tr>
            <c:forEach items="${patients}" var="prescription">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/doctor/patient?id=${prescription.id}">${prescription.fullName}</a></td>
                    <td>${prescription.patientStatus}</td>
                    <td>${prescription.insuranceNumber}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>