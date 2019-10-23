<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>Patients</h1>
    </div>
    <div class="container-fluid">
        <nav class="navbar navbar-light bg-light">
            <form class="form-inline">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
            <form class="form-inline">
                <button class="btn btn-outline-success" type="button"  data-toggle="modal" data-target="#newPatientModal">New patient</button>
            </form>
        </nav>
        <table class="table">
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Status</th>
                <th scope="col">Diagnosis</th>
                <th scope="col">Insurance</th>
            </tr>
            <c:forEach var="prescription" items="${patients}" >
                <tr>
                    <td><a href="${pageContext.request.contextPath}/doctor/patient?id=${prescription.id}">${prescription.fullName}</a></td>
                    <td>${prescription.patientStatus}</td>
                    <td>${prescription.diagnosis}</td>
                    <td>${prescription.insuranceNumber}</td>
                </tr>
            </c:forEach>
        </table>

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
                            <input type="text" class="form-control" id="newPatientFullName" placeholder="Full name" name="fullName">
                        </div>
                        <div class="form-group">
                            <label for="newPatientInsurance">Insurance</label>
                            <input type="text" class="form-control" id="newPatientInsurance" placeholder="Insurance" name="insuranceNumber">
                        </div>
                        <div class="form-group">
                            <label for="newPatientDiagnosis">Diagnosis</label>
                            <input type="text" class="form-control" id="newPatientDiagnosis" placeholder="Diagnosis" name="diagnosis">
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

    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>