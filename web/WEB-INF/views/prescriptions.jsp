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
        </nav>
    </div>
    <div class="container-fluid">
        <table class="table">
            <tr>
                <th scope="col">Date</th>
                <th scope="col">Patient</th>
                <th scope="col">Type</th>
                <th scope="col">Treatment</th>
                <th scope="col">Dosage</th>
                <th scope="col">Pattern</th>
                <th scope="col">Days</th>
                <th scope="col">Doctor</th>
            </tr>
            <c:forEach items="${prescriptions}" var="prescription">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/doctor/prescription?id=${prescription.id}">${prescription.dateTimeFormatted}</a></td>
                    <td>${prescription.patient.fullName}</td>
                    <td>${prescription.treatmentType}</td>
                    <td>${prescription.treatmentName}</td>
                    <td>${prescription.dosage}</td>
                    <td>${prescription.patternName}</td>
                    <td>${prescription.duration}</td>
                    <td>${prescription.doctorFullName}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>