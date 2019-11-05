<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

    <div>
        <div class="container-fluid">
            <h1>Patients</h1>
            <p></p>
        </div>
        <div class="container-fluid">
            <div>
                <table id="patientsTable" class="table table-hover">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Status</th>
                            <th>Diagnosis</th>
                            <th>Insurance</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>

<!-- New Patient Modal -->
<div class="modal fade" id="newPatientModal" tabindex="-1" role="dialog" aria-labelledby="newPatientModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/doctor/newPatient" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="newPatientModalTitle">New patient</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="newPatientFullName">Full name</label>
                        <input type="text" class="form-control" id="newPatientFullName" placeholder="Full name"
                               name="fullName" required>
                    </div>
                    <div class="form-group">
                        <label for="newPatientInsurance">Insurance</label>
                        <input type="text" class="form-control" id="newPatientInsurance" placeholder="Insurance"
                               name="insuranceNumber" required>
                    </div>
                    <div class="form-group">
                        <label for="newPatientDiagnosis">Diagnosis</label>
                        <input type="text" class="form-control" id="newPatientDiagnosis" placeholder="Diagnosis"
                               name="diagnosis" required>
                    </div>
                    <input type="hidden" name="patientStatus" value="PATIENT">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save patient</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/lib/datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/patients.js"></script>
</jsp:attribute>
    <jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/datatables.min.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>