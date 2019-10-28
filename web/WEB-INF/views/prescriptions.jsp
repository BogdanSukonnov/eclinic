<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>Prescriptions</h1>
        <p></p>
    </div>
    <div class="container-fluid">
        <div>
            <table id="prescriptionsTable" class="table table-hover" style="width:100%">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Patient</th>
                    <th>Type</th>
                    <th>Treatment</th>
                    <th>Dosage</th>
                    <th>Pattern</th>
                    <th>Days</th>
                    <th>Doctor</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/lib/datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/prescriptions.js"></script>
</jsp:attribute>
    <jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/datatables.min.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>