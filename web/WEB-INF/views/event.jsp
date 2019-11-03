<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

    <div>
        <h1>Event</h1>
        <input type="hidden" id="eventId" value="${event.id}">
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
            <c:if test="${event.treatmentType.equals('MEDICINE')}">
                <div class="prop-row">
                    <div class="field-label">Dosage:</div>
                    <div class="field">${event.dosage}</div>
                </div>
            </c:if>
            <div class="prop-row">
                <div class="field-label">Status:</div>
                <div class="field">${event.eventStatus}</div>
            </div>
            <c:if test="${event.eventStatus != 'SCHEDULED'}">
                <c:if test="${event.eventStatus == 'CANCELED'}">
                    <div class="prop-row">
                        <div class="field-label">Reason:</div>
                        <div class="field">${event.cancelReason}</div>
                    </div>
                </c:if>
                <div class="prop-row">
                    <div class="field-label">Nurse:</div>
                    <div class="field">${event.nurseFullName}</div>
                </div>
            </c:if>
            <div class="prop-row">
                <div class="field-label">Updated:</div>
                <div class="field">${event.updatedDateFormatted}</div>
            </div>
            <div class="prop-row">
                <button id="backBtn" type="button" class="btn btn-outline-secondary"
                        onclick="window.history.back()">Back
                </button>
                <c:if test="${event.eventStatus == 'SCHEDULED'}">
                    <button id="cancelEventBtn" type="button" class="btn btn-outline-danger"
                            data-toggle="modal" data-target="#cancelEventModal">
                        Cancel an event
                    </button>
                    <button id="completeEventBtn" type="button" class="btn btn-outline-success">
                        Complete
                    </button>
                </c:if>
            </div>
                <%--    cancel modal   --%>
            <div class="modal fade" id="cancelEventModal" tabindex="-1" role="dialog"
                 aria-labelledby="cancelEventModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="cancelEventModalLabel">Cancel event</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div id="cancellingReasonGroup" class="form-group">
                                    <label for="cancellingReason">Reason</label>
                                    <input type="text" class="form-control" id="cancellingReason"
                                           placeholder="Please enter the reason" name="reason">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                            <span id="submitReasonPopoverSpan" class="d-inline-block" data-toggle="popover"
                                  data-content="Please enter the reason">
                            <button id="submitReasonBtn" style="pointer-events: none;" type="button"
                                    class="btn btn-outline-success" disabled>Submit the reason</button>
                        </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--Common part start--%>
</jsp:attribute>
    <jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/script/event.js"></script>
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>