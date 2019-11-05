<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

    <div class="container-fluid">

        <form id="prescriptionForm" method="post"
            <c:if test="${isNew}">
                action="${pageContext.request.contextPath}/doctor/saveNewPrescription"
              </c:if>
                    <c:if test="${!isNew}">
                action="${pageContext.request.contextPath}/doctor/updatePrescription"
              </c:if> >

            <div class="form-group">
                <h1>${isNew ? "New" : prescription.status == "PRESCRIBED" ? "Edit": prescription.status} prescription</h1>
            </div>

            <div class="form-group row">
                <p class="col-sm-2 col-form-label">Patient:</p>
                <h5 class="col-sm-10">${patientFullName}${((!isNew && prescription.patient.patientStatus == "DISCHARGED") ? " - DISCHARGED" : "")}</h5>
                <input type="hidden" id="patientStatus" value="${isNew ? "" : prescription.patient.patientStatus}">
            </div>

                <%--      period      --%>
            <div class="form-group row">
                <label for="period" class="col-sm-2 col-form-label">Period</label>
                <div class="col-sm-2">
                    <input id="period" type="text" name="period" class="form-control" >
                    <input id="startDate" type="hidden" name="startDate" value="${prescription.startDateFormatted}">
                    <input id="endDate" type="hidden" name="endDate" value="${prescription.endDateFormatted}">
                </div>
            </div>

                <%--      treatment type      --%>
            <div class="form-group row">
                <label class="col-2 col-form-label">Type</label>
                <div class="col-5">
                    <div id="treatmentTypeContainer">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio1"
                                   value="PROCEDURE"
                                ${isNew ? "checked" : prescription.treatment.type == "PROCEDURE" ? "checked" : ""}>
                            <label class="form-check-label" for="inlineRadio1">Procedure</label>
                        </div>
                        <div class="col-2 form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio2"
                                   value="MEDICINE"
                                ${isNew ? "" : prescription.treatment.type == "MEDICINE" ? "checked" : ""}>
                            <label class="form-check-label" for="inlineRadio2">Medicine</label>
                        </div>
                    </div>
                </div>
            </div>

                <%--      treatment      --%>
            <div class="form-group row justify-content-start" id="treatmentGroup">
                <label for="treatment" class="col-2 col-form-label">Treatment</label>
                <div class="col-5">
                    <select name="treatmentId" id="treatment">
                        <c:if test="${isNew}">
                            <option selected>Select procedure</option>
                        </c:if>
                        <c:if test="${!isNew}">
                            <option selected
                                    value="${prescription.treatment.id}">${prescription.treatment.name}</option>
                        </c:if>
                    </select>
                </div>
            </div>

                <%--      dosage      --%>
            <div id="dosageGroup" class="form-group row">
                <label for="dosage" class="col-sm-2 col-form-label">Dosage</label>
                <div class="col-sm-3">
                    <input type="text" id="dosage" class="form-control"
                           placeholder="Dosage" name="dosage" value="${prescription.dosage}">
                </div>
            </div>

                <%--      pattern      --%>
            <div id="dosageGroup" class="form-group row">
                <label for="pattern" class="col-sm-2 col-form-label">Pattern</label>
                <div class="col-sm-7">
                    <select name="timePatternId" id="pattern">
                    <c:if test="${isNew}">
                        <option selected>Select time pattern</option>
                    </c:if>
                        <c:if test="${!isNew}">
                        <option selected
                                value="${prescription.timePattern.id}">${prescription.timePattern.name}</option>
                    </c:if>
                    </select>
                </div>
            </div>

                <%--    buttons    --%>
            <div class="form-row align-items-center">
                <div class="col-auto my-1">
                    <button type="button" class="btn btn-secondary" onclick="window.history.back();">
                        &nbsp;&nbsp;&nbsp;Back&nbsp;&nbsp;&nbsp;
                    </button>
                </div>
                <c:if test="${isNew || prescription.status == 'PRESCRIBED'}">
                    <div class="col-auto my-1">
                        <button type="submit" class="btn btn-primary edit-button">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Save&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </button>
                    </div>
                </c:if>
                <c:if test="${!isNew && prescription.status == 'PRESCRIBED'}">
                    <div class="col-auto my-1">
                        <button id="cancelButton" type="button" class="btn btn-outline-danger edit-button"
                                data-toggle="modal" data-target="#cancelModal">
                            Cancel prescription
                        </button>
                    </div>
                </c:if>
            </div>
            <div hidden>
                <input id="prescriptionId" type="hidden" name="id" value=${isNew ? null : prescription.id}>
                <input id="status" type="hidden" name="status" value=${isNew ? null : prescription.status}>
                <input type="hidden" name="patientId" id="patientId" value=${patientId}>
                <input type="hidden" name="version" value=${prescription.version}>
            </div>
        </form>

        <form>
            <div class="form-group">
                <table id="prescriptionEventsTable" class="table table-hover" style="width:100%">
                    <thead>
                    <tr>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Patient</th>
                        <th>Type</th>
                        <th>Treatment</th>
                        <th>Dosage</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </form>

    </div>

    <!-- Modal -->
<div class="modal fade" id="cancelModal" tabindex="-1" role="dialog" aria-labelledby="cancelModalTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="cancelModalTitle">Cancel prescription</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Are you sure?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                <button id="cancelPrescriptionBtn" type="button" class="btn btn-outline-danger">Cancel prescription
                </button>
            </div>
        </div>
    </div>
</div>

    <%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/lib/datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/lib/select2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/script/lib/moment.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/script/lib/daterangepicker.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/prescription.js"></script>
</jsp:attribute>
<jsp:attribute name="pageStyles">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/lib/daterangepicker.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/datatables.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/select2.min.css"><link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/lib/daterangepicker.css" />
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>