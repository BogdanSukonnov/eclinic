<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>Prescription</h1>
        <div id="prescriptionWrapper">
            <div id="prescriptionLeft"></div>
            <form id="prescriptionForm" action="${pageContext.request.contextPath}/doctor/saveNewPrescription" method="post">
                <div class="form-group row">
                    <p class="col-sm-2">Patient:</p>
                    <h5 class="col-sm-10">${patient_fullName}</h5>
                    <input type="hidden" name="patient_id" value=${patient_id} />
                </div>

                    <%--      treatment type      --%>
                <div class="form-group">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio1"
                        value="Procedure" checked >
                        <label class="form-check-label" for="inlineRadio1">Procedure</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio2"
                        value="Medicine">
                        <label class="form-check-label" for="inlineRadio2">Medicine</label>
                    </div>
                </div>

                    <%--      procedure      --%>
                <div class="form-group row">
                    <label for="procedure" class="col-sm-2 col-form-label">Procedure</label>
                    <select name="procedure_id" class="custom-select col-sm-10" id="procedure">
                        <option selected>Select the procedure</option>
                        <c:forEach var="currProcedure" items="${allProcedures}">
                            <option value=${currProcedure.id}>${currProcedure.name}</option>
                        </c:forEach>
                    </select>
                </div>

                    <%--      medicine      --%>
                <div class="form-group row">
                    <label for="medicine" class="col-sm-2 col-form-label">Medicine</label>
                    <select name="medicine_id" class="custom-select col-sm-10" id="medicine">
                        <option selected>Select the medicine</option>
                        <c:forEach var="currMedicine" items="${allMedicine}">
                            <option value=${currMedicine.id}>${currMedicine.name}</option>
                        </c:forEach>
                    </select>
                </div>

                    <%--      pattern      --%>
                <div class="form-group row">
                    <label for="pattern" class="col-sm-2 col-form-label">Pattern</label>
                    <select name="pattern_id" class="custom-select col-sm-10" id="pattern">
                        <option selected>Select the pattern</option>
                        <c:forEach var="currMedicine" items="${allPatterns}">
                            <option value=${currMedicine.id}>${currMedicine.name}</option>
                        </c:forEach>
                    </select>
                </div>

                    <%--      dosage      --%>
                <div class="form-group row">
                    <label for="dosage" class="col-sm-2 col-form-label">Dosage</label>
                    <input type="number" class="form-control col-sm-10" id="dosage" placeholder="Dosage" name="dosage">
                </div>

                    <%--      duration      --%>
                <div class="form-group row">
                    <label for="duration" class="col-sm-2 col-form-label">Duration</label>
                    <input type="number" class="form-control col-sm-10" id="duration" placeholder="Duration" name="duration">
                </div>

                <button type="submit" class="btn btn-primary">Save</button>
            </form>
            <div id="prescriptionRight"></div>
        </div>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>