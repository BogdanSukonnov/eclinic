<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div class="container-fluid">
        <form>
        <div id="eventsTitleRow" class="form-row">
            <div class="">
                <h1>Events</h1>
            </div>
            <div id="eventsFiltersRaw" class="form-row">
                <div class="col">
                    <input id="showCompleted" type="checkbox" class="form-check-input">
                    <label for="showCompleted" class="form-check-label" >Show completed</label>
                </div>
                <div class="col">
                    <input id="eventsDates" type="text" name="eventsDates" class="form-control" >
                </div>
            </div>
        </div>
        <div>
            <table id="eventsTable" class="table table-hover">
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
</div>

<%--Common part start--%>
</jsp:attribute>
<jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/lib/datatables.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/script/lib/moment.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/script/lib/daterangepicker.js"></script>
    <script src="${pageContext.request.contextPath}/static/script/events.js"></script>
</jsp:attribute>
<jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/datatables.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/lib/daterangepicker.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/events.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>