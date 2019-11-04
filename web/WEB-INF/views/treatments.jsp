<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

    <div>
        <div class="container-fluid">
            <h1>Treatments</h1>
            <p></p>
        </div>
        <div class="container-fluid">
            <div>
                <table id="treatmentsTable" class="table">
                    <thead>
                        <tr>
                            <th>Type</th>
                            <th>Name</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>

<!-- New Treatment Modal -->
<div class="modal fade" id="newTreatmentModal" tabindex="-1" role="dialog" aria-labelledby="newTreatmentModalTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/doctor/new-treatment" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="newTreatmentModalTitle">New treatment</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                        <%--      treatment type      --%>
                    <div class="form-group">
                        <label>Type</label>
                        <div>
                            <div id="treatmentTypeContainer">
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio1"
                                           value="PROCEDURE">
                                    <label class="form-check-label" for="inlineRadio1">Procedure</label>
                                </div>
                                <div class="col-2 form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="treatmentType" id="inlineRadio2"
                                           value="MEDICINE">
                                    <label class="form-check-label" for="inlineRadio2">Medicine</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newTreatmentName">name</label>
                        <input type="text" class="form-control" id="newTreatmentName" placeholder="Full name" name="name">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" id="saveTreatmentBtn" class="btn btn-primary" disabled>Save treatment</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/lib/datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/treatments.js"></script>
</jsp:attribute>
    <jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/datatables.min.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>