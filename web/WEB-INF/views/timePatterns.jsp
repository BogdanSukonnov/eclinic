<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

    <div>
        <div class="container-fluid">
            <h1>Time patterns</h1>
            <p></p>
        </div>
        <div class="container-fluid">
            <div>
                <table id="timePatternsTable" class="table table-hover">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Cycle Length</th>
                            <th>Is week cycle</th>
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
    <script src="${pageContext.request.contextPath}/static/script/timePatterns.js"></script>
</jsp:attribute>
    <jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/lib/datatables.min.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>