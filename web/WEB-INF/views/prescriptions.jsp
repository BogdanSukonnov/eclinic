<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>Prescriptions</h1>
        <nav class="navbar navbar-light bg-light">
            <form class="form-inline">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
            <a class="btn btn-outline-success my-2 my-sm-0" href="">
                New prescription
            </a>
        </nav>
    </div>
    <div class="container-fluid">
        <table class="table">
            <tr>
                <th scope="col">Date</th>
                <th scope="col">Patient</th>
                <th scope="col">Type</th>
                <th scope="col">Pattern</th>
                <th scope="col">Days</th>
                <th scope="col">Doctor</th>
            </tr>
            <c:forEach items="${prescriptions}" var="patient">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/doctor/prescription?id=${patient.id}">${patient.dateTimeFormatted}</a></td>
                    <td>${patient.patient.fullName}</td>
                    <td>${patient.treatmentType}</td>
                    <td>${patient.patternName}</td>
                    <td>${patient.duration}</td>
                    <td>${patient.doctorFullName}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>