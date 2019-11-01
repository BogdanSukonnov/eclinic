<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>${isNew ? "New" : prescription.status == "ACTIVE" ? "Edit": prescription.status} prescription</h1>
        <div id="prescriptionWrapper">
            <div id="prescriptionLeft"></div>
            <form id="prescriptionForm" method="post"
                  <c:if test="${isNew}">
                    action="${pageContext.request.contextPath}/doctor/saveNewPrescription"
                  </c:if>
                  <c:if test="${!isNew}">
                    action="${pageContext.request.contextPath}/doctor/updatePrescription"
                  </c:if> >

                <input id="prescriptionId" type="hidden" name="id" value=${isNew ? null : prescription.id} >
                <input id="status" type="hidden" name="status" value=${isNew ? null : prescription.status} >

                <div class="form-group row">
                    <p class="col-sm-2">Patient:</p>
                    <h5 class="col-sm-10">${patientFullName}</h5>
                    <input type="hidden" name="patientId" value=${patientId} />
                </div>

                    <%--      treatment type      --%>
                <div class="form-group">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio1"
                               value="Procedure"
                            ${isNew ? "checked" : prescription.treatment.type == "Procedure" ? "checked" : ""}>
                        <label class="form-check-label" for="inlineRadio1">Procedure</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio2"
                               value="Medicine"
                            ${isNew ? "" : prescription.treatment.type == "Medicine" ? "checked" : ""}>
                        <label class="form-check-label" for="inlineRadio2">Medicine</label>
                    </div>
                </div>

                    <%--      treatment      --%>
                <div class="form-group row" id="treatmentGroup">
                    <label for="treatment" class="col-sm-2 col-form-label">Treatment</label>
                    <select name="treatmentId" class="custom-select col-sm-10" id="treatment">
                        <c:if test="${isNew}">
                            <option selected>Select procedure</option>
                        </c:if>
                        <c:if test="${!isNew}">
                            <option selected value="${prescription.treatment.id}">${prescription.treatment.name}</option>
                        </c:if>
                    </select>
                </div>

                    <%--      dosage      --%>
                <div id="dosageGroup" class="form-group row">
                    <label for="dosage" class="col-sm-2 col-form-label">Dosage</label>
                    <input type="text" class="form-control col-sm-10" id="dosage"
                           placeholder="Dosage" name="dosage" value="${prescription.dosage}">
                </div>

                    <%--      pattern      --%>
                <div class="form-group row">
                    <label for="pattern" class="col-sm-2 col-form-label">Pattern</label>
                    <select name="timePatternId" class="custom-select col-sm-10" id="pattern" >
                        <c:if test="${isNew}">
                            <option selected>Select time pattern</option>
                        </c:if>
                        <c:if test="${!isNew}">
                            <option selected value="${prescription.timePattern.id}">${prescription.timePattern.name}</option>
                        </c:if>
                    </select>
                </div>

                    <%--      duration      --%>
                <div class="form-group row">
                    <label for="duration" class="col-sm-2 col-form-label">Duration</label>
                    <input type="number" class="form-control col-sm-10" id="duration"
                           placeholder="Duration (days)" name="duration"
                            value="${prescription.duration}">
                </div>

                <button type="button" class="btn btn-outline-secondary" onclick="window.history.back();">Back</button>
                <c:if test="${isNew || prescription.status == 'ACTIVE'}">
                    <button type="submit" class="btn btn-outline-primary">Save</button>
                </c:if>
                <c:if test="${!isNew && prescription.status == 'ACTIVE'}">
                    <button id="cancelButton" type="button" class="btn btn-outline-danger"
                            data-toggle="modal" data-target="#cancelModal">Cancel prescription</button>
                </c:if>
            </form>
            <div id="prescriptionRight"></div>
        </div>
        <div>
            <table id="prescriptionEventsTable" class="table table-hover" style="width:100%">
                <thead>
                <tr>
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
    </div>
</div>

    <!-- Modal -->
<div class="modal fade" id="cancelModal" tabindex="-1" role="dialog" aria-labelledby="cancelModalTitle" aria-hidden="true">
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
                <button id="cancelPrescriptionBtn" type="button" class="btn btn-outline-danger">Cancel prescription</button>
            </div>
        </div>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/prescription.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/lib/datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/lib/select2.min.js"></script>
</jsp:attribute>
<jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/datatables.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/select2.min.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>