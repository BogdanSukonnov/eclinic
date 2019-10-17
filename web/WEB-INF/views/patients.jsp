<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <h1>Patients</h1>
    <div class="container-fluid">
        <table class="table">
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Status</th>
                <th scope="col">Insurance</th>
            </tr>
            <c:forEach items="${patients}" var="patient" varStatus="status">
                <tr>
                    <td>${patient.fullName}</td>
                    <td>${patient.patientStatus}</td>
                    <td>${patient.insuranceNumber}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>