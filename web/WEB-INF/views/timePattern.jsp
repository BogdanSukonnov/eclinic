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
                <h3 class="field">${timePattern.name}</h3>
            </div>
            <div class="prop-row">
                <div class="field-label">Cycle (days):</div>
                <div class="field">${timePattern.isWeekCycle ? "Week" : timePattern.cycleLength}</div>
            </div>
            <c:if test="${!timePattern.isWeekCycle}">
                    <div class="prop-row">
                        <div class="field-label">Is week cycle:</div>
                        <div class="field">${timePattern.isWeekCycle}</div>
                    </div>
                </c:if>

            <div class="prop-row">
                <div class="field-label"></div>
                <div class="field"></div>
            </div>

            <table id="timePatternItemsTable" class="col-sm-3 table table-hover table-striped">
                <thead>
                <tr>
                    <th>dayOfCycle</th>
                    <th>time</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${timePattern.items}">
                    <tr>
                        <td>${!timePattern.isWeekCycle ? item.dayOfCycle + 1 : item.dayOfCycle == 0
                                ? "Sun" : item.dayOfCycle == 1 ? "Mon" : item.dayOfCycle == 2
                                ? "Tue" : item.dayOfCycle == 3 ? "Wed" : item.dayOfCycle == 4
                                ? "Thu" : item.dayOfCycle == 5 ? "Fri" : item.dayOfCycle == 6
                                ? "Sat" : item.dayOfCycle + 1}</td>
                        <td>${item.time}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>

    <%--Common part start--%>
</jsp:attribute>
    <jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/timePattern.js"></script>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>