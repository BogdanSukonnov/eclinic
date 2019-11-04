<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

    <div>
        <h1>Time pattern</h1>
        <input type="hidden" id="id" value="${timePattern.id}">
        <input type="hidden" id="version" value="${timePattern.version}">
        <div class="prop-list">
            <div class="prop-row">
                <div class="field-label">Name:</div>
                <div class="field">${timePattern.name}</div>
            </div>
            <div class="prop-row">
                <div class="field-label">Cycle Length:</div>
                <div class="field">${timePattern.cycleLength}</div>
            </div>
            <div class="prop-row">
                <div class="field-label">Is week cycle:</div>
                <div class="field">${timePattern.isWeekCycle}</div>
            </div>
        </div>
    </div>

    <table id="timePatternItemsTable" class="table table-hover">
        <thead>
            <tr>
                <th>dayOfCycle</th>
                <th>time</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${timePattern.items}">
                <tr>
                    <td>${item.dayOfCycle}</td>
                    <td>${item.time}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <%--Common part start--%>
</jsp:attribute>
    <jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/timePattern.js"></script>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>