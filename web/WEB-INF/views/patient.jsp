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
            <input type="hidden" id="version" value="${patient.version}" />
            <input type="hidden" id="patient_fullName" value="${patient.fullName}" />
            <input type="hidden" id="patient_status" value="${patient.patientStatus}" />
        </div>

        <div class="container-fluid">
            <table id="patientPrescriptionsTable" class="table table-hover" style="width:100%">
                <thead>
                <tr>
                    <th>Status</th>
                    <th>Period</th>
                    <th>Patient</th>
                    <th>Type</th>
                    <th>Treatment</th>
                    <th>Pattern</th>
                    <th>Doctor</th>
                </tr>
                </thead>
            </table>
        </div>

    </div>
</div>
    
        <!-- Modal -->
<div class="modal fade" id="dischargeModal" tabindex="-1" role="dialog" aria-labelledby="cancelModalTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cancelModalTitle">Discharge patient</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Are you sure?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">
                    Close
                </button>
                <button id="dischargeConfirmedBtn" type="button" class="btn btn-outline-danger">
                    Discharge patient
                </button>
            </div>
        </div>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/lib/datatables/datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/patient.js"></script>
</jsp:attribute>
<jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/datatables/datatables.min.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>