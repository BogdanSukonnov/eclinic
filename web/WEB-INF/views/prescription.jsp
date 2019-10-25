<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>${empty prescription ? "New" : "Edit"} prescription</h1>
        <div id="prescriptionWrapper">
            <div id="prescriptionLeft"></div>
            <form id="prescriptionForm" method="post"
                  <c:if test="${empty prescription}">
                    action="${pageContext.request.contextPath}/doctor/saveNewPrescription"
                  </c:if>
                  <c:if test="${not empty prescription}">
                    action="${pageContext.request.contextPath}/doctor/updatePrescription"
                  </c:if> >

                <input type="hidden" name="id" value=${empty prescription ? null : prescription.id} >

                <div class="form-group row">
                    <p class="col-sm-2">Patient:</p>
                    <h5 class="col-sm-10">${patient_fullName}</h5>
                    <input type="hidden" name="patientId" value=${patient_id} />
                </div>

                    <%--      treatment type      --%>
                <div class="form-group">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio1"
                               value="Procedure"
                            ${empty prescription ? "checked" : prescription.treatmentType == "Procedure" ? "checked" : ""}>
                        <label class="form-check-label" for="inlineRadio1">Procedure</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio2"
                               value="Medicine"
                            ${empty prescription ? "" : prescription.treatmentType == "Medicine" ? "checked" : ""}>
                        <label class="form-check-label" for="inlineRadio2">Medicine</label>
                    </div>
                </div>

                    <%--      treatment      --%>
                <div class="form-group row" id="treatmentGroup">
                    <label for="treatment" class="col-sm-2 col-form-label">Treatment</label>
                    <select name="treatmentId" class="custom-select col-sm-10" id="treatment"></select>
                        <%--         inject objects to javascript           --%>
                    <script>
                        let treatmentId = ${empty prescription ? 0 : prescription.treatmentId};
                        let allTreatments = {
                            <c:forEach items="${allTreatments}" var="treatment">
                                "${treatment.id}": {
                                    name:"${treatment.name}",
                                    treatmentType:"${treatment.type}",
                                },
                            </c:forEach>
                        }
                    </script>
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
                    <select name="patternId" class="custom-select col-sm-10" id="pattern" >
                        <c:if test="${empty prescription}">
                            <option selected>Select time pattern</option>
                        </c:if>
                        <c:forEach var="currPattern" items="${allPatterns}">
                            <option value=${currPattern.id}
                                        <c:if test="${prescription.patternId == currPattern.id}">
                                                selected
                                        </c:if> >
                               ${currPattern.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                    <%--      duration      --%>
                <div class="form-group row">
                    <label for="duration" class="col-sm-2 col-form-label">Duration</label>
                    <input type="number" class="form-control col-sm-10" id="duration"
                           placeholder="Duration (days)" name="duration"
                            value="${prescription.duration}">
                </div>

                <button type="submit" class="btn btn-primary">Save</button>
                <button type="button" class="btn btn-secondary" onclick="window.history.back();">Cancel</button>
            </form>
            <div id="prescriptionRight"></div>
        </div>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/prescription.js"></script>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>