<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1><c:choose>
            <c:when test="${empty prescription}">New</c:when>
            <c:otherwise>Edit</c:otherwise>
            </c:choose> prescription</h1>
        <div id="prescriptionWrapper">
            <div id="prescriptionLeft"></div>
            <form id="prescriptionForm" method="post"
                  <c:if test="${empty prescription}">
                    action="${pageContext.request.contextPath}/doctor/saveNewPrescription"
                  </c:if>
                  <c:if test="${not empty prescription}">
                    action="${pageContext.request.contextPath}/doctor/updatePrescription"
                  </c:if>
            >
                <div class="form-group row">
                    <p class="col-sm-2">Patient:</p>
                    <h5 class="col-sm-10">${patient_fullName}</h5>
                    <input type="hidden" name="patient_id" value=${patient_id} />
                </div>

                    <%--      treatment type      --%>
                <div class="form-group">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio1"
                               value="Procedure" <c:choose>
                                                    <c:when test="${empty prescription}">
                                                           checked
                                                    </c:when>
                                                    <c:when test="${prescription.treatmentType == 'Procedure'}">
                                                           checked
                                                    </c:when>
                                                    </c:choose> >
                        <label class="form-check-label" for="inlineRadio1">Procedure</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio2"
                               value="Medicine" <c:choose>
                                                <c:when test="${empty prescription}">
                                                </c:when>
                                                <c:when test="${prescription.treatmentType == 'Medicine'}">
                                                       checked
                                                </c:when>
                                                </c:choose>>
                        <label class="form-check-label" for="inlineRadio2">Medicine</label>
                    </div>
                </div>

                    <%--      procedure      --%>
                <div class="form-group row" id="procedureGroup">
                    <label for="procedure" class="col-sm-2 col-form-label">Procedure</label>
                    <select name="procedure_id" class="custom-select col-sm-10" id="procedure">
                        <option <c:if test="${empty prescription}">
                                    selected
                                </c:if>>
                            Select the procedure
                        </option>
                        <c:forEach var="currProcedure" items="${allProcedures}">
                            <option value=${currProcedure.id}
                                            <c:if test="${prescription.treatmentId == currProcedure.id}">
                                                selected
                                            </c:if> >
                                    ${currProcedure.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                    <%--      medicine      --%>
                <div class="form-group row"  id="medicineGroup">
                    <label for="medicine" class="col-sm-2 col-form-label">Medicine</label>
                    <select name="medicine_id" class="custom-select col-sm-10" id="medicine">
                        <option <c:if test="${empty prescription}">
                                    selected
                                </c:if> >
                            Select the medicine
                        </option>
                        <c:forEach var="currPattern" items="${allMedicine}">
                            <option value=${currPattern.id}
                                            <c:if test="${prescription.treatmentId == currPattern.id}">
                                                selected
                                            </c:if> >
                                    ${currPattern.name}
                            </option>
                        </c:forEach>
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
                    <select name="pattern_id" class="custom-select col-sm-10" id="pattern">
                        <option <c:if test="${empty prescription}">
                                    selected
                                </c:if> >
                            Select the pattern
                        </option>
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
</t:generic-page>
<%--Common part end--%>