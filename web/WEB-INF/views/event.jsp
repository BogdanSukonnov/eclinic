<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

<div>
    <div>
        <h1>Event</h1>

        <div class="prop-list">
            <div class="prop-row">
                <div class="field-label">Patient:</div>
                <div class="field">${event.patientFullName}</div>
            </div>
            <div class="prop-row">
                <div class="field-label">Date:</div>
                <div class="field">${event.dateFormatted}</div>
            </div>
            <div class="prop-row">
                <div class="field-label">Time:</div>
                <div class="field">${event.timeFormatted}</div>
            </div>
            <div class="prop-row">
                <div class="field-label">${event.treatmentType}:</div>
                <div class="field">${event.treatmentName}</div>
            </div>
            <c:if test="${event.treatmentType.equals('Medicine')}">
                <div class="prop-row">
                    <div class="field-label">Dosage:</div>
                    <div class="field">${event.dosage}</div>
                </div>
            </c:if>
            <div class="prop-row">
                <div class="field-label">Status:</div>
                <div class="field">${event.eventStatus}</div>
            </div>
        </div>

    </div>
</div>

<%--Common part start--%>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>