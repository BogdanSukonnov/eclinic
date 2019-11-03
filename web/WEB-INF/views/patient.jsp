<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>Patient - ${patient.fullName}</h1>

        <div class="prop-list">
            <div class="prop-row">
                <div class="field-label">Full name:</div>
                <div class="field">${patient.fullName}</div>
            </div>
            <div class="prop-row">
                <div class="field-label">Insurance:</div>
                <div class="field">${patient.insuranceNumber}</div>
            </div>
            <div class="prop-row">
                <div class="field-label">Diagnosis:</div>
                <div class="field">${patient.diagnosis}</div>
            </div>
            <div class="prop-row">
                <div class="field-label">Status:</div>
                <div class="field">${patient.patientStatus}</div>
             </div>
        </div>

        <div hidden>
            <input type="hidden" id="patient_id" value="${patient.id}" />
            <input type="hidden" id="patient_fullName" value="${patient.fullName}" />
        </div>

        <div class="container-fluid">
            <table id="patientPrescriptionsTable" class="table table-hover" style="width:100%">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Patient</th>
                        <th>Type</th>
                        <th>Treatment</th>
                        <th>Dosage</th>
                        <th>Pattern</th>
                        <th>Days</th>
                        <th>Doctor</th>
                    </tr>
                </thead>
            </table>
        </div>

    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/lib/datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/patient.js"></script>
</jsp:attribute>
<jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/datatables.min.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>