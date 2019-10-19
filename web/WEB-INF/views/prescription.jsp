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
        <form>
            <div class="form-group row">
                <label for="patient_fullName" class="col-sm-2 col-form-label">Patient</label>
                <div class="col-sm-10">
                    <input type="hidden" name="patient_id" value=${patient_id} />
                    <input type="text" readonly class="form-control-plaintext" id="patient_fullName" value="${patient_fullName}">
                </div>
            </div>
            <div class="form-group">
                <label for="treatmentType">Type</label>
                <select class="form-control" id="treatmentType" name="treatment_type">
                    <option>Procedure</option>
                    <option>Medicine</option>
                </select>
            </div>
            <div class="form-group">
                <label for="pattern">Pattern</label>
                <input type="text" class="form-control" id="pattern" placeholder="Pattern">
            </div>
            <div class="form-group">
                <label for="dosage">Dosage</label>
                <input type="number" class="form-control" id="dosage" placeholder="Dosage" name="dosage">
            </div>
            <div class="form-group">
                <label for="duration">Duration</label>
                <input type="number" class="form-control" id="duration" placeholder="Duration" name="duration">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>