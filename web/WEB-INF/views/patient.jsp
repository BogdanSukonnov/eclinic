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

            <form id="newPrescriptionButtonForm" action="${pageContext.request.contextPath}/doctor/newPrescription" method="post">
                <input type="hidden" name='patient_id' value="${patient.id}" />
                <input type="hidden" name='patient_fullName' value="${patient.fullName}" />
                <button type="submit" class="btn btn-light">New perscription</button>
            </form>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>