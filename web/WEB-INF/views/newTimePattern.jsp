<%--Common part start--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:generic-page>
<jsp:attribute name="content">
<%--Common part end--%>

    <div class="container-fluid">

        <form id="newTimePatternForm">

            <div class="form-group">
                <h1>New time pattern</h1>
            </div>

                <%--      name      --%>
            <div class="form-group row">
                <label for="name" class="col-sm-1 col-form-label">Name</label>
                <div class="col-sm-7">
                    <input type="text" id="name" class="form-control" placeholder="Name"
                           name="name">
                </div>
            </div>

                <%--      period type      --%>
            <div class="form-group row">
                <label class="col-sm-1 col-form-label"></label>
                <div id="periodTypeContainer" class="col-sm-10">
                    <div class="pattern-type-radio-group custom-control custom-radio custom-control-inline">
                        <input class="custom-control-input" type="radio" name="periodType" id="inlineRadio1"
                               value="Daily" checked>
                        <label class="custom-control-label" for="inlineRadio1">Daily</label>
                    </div>
                    <div class="pattern-type-radio-group custom-control custom-radio custom-control-inline">
                        <input class="custom-control-input" type="radio" name="periodType" id="inlineRadio2"
                               value="Weekly">
                        <label class="custom-control-label" for="inlineRadio2">Weekly</label>
                    </div>
                    <div class="pattern-type-radio-group custom-control custom-radio custom-control-inline">
                        <input class="custom-control-input" type="radio" name="periodType" id="inlineRadio3"
                               value="Every">
                        <label class="custom-control-label" for="inlineRadio3">Every n days</label>
                    </div>
                    <div class="pattern-type-radio-group custom-control custom-radio custom-control-inline">
                        <input class="custom-control-input" type="radio" name="periodType" id="inlineRadio4"
                               value="Custom">
                        <label class="custom-control-label" for="inlineRadio4">Custom</label>
                    </div>
                </div>
            </div>

            <div class="customPatternGroup form-group row" hidden>
                <label for="customLength" class="col-sm-1 col-form-label">Cycle length</label>
                <div class="col-sm-2">
                    <select class="custom-select" type="" name="customLength" id="customLength">
                        <option value=1>1</option>
                        <option value=2>2</option>
                        <option value=3 selected>3</option>
                        <option value=4>4</option>
                        <option value=5>5</option>
                        <option value=6>6</option>
                        <option value=7>7</option>
                        <option value=8>8</option>
                        <option value=Week>Week</option>
                    </select>
                </div>
            </div>

            <div class="form-group row customPatternGroup">
                <label class="col-sm-1 col-form-label">New time:</label>

                <div class="col-sm-2">

                    <label for="newDayNumber" class="col-form-label">Day number</label>
                    <select class="custom-select" type="" name="newDayNumber" id="newDayNumber">
                        <option value=1>1</option>
                        <option value=2>2</option>
                        <option value=3>3</option>
                    </select>

                    <label for="newDayTime" class="col-form-label">Time</label>
                    <input type="time" id="newDayTime" name="newDayTime"
                           min="00:00" max="23:59" class="form-control">
                </div>
            </div>
            <div class="form-group row customPatternGroup">
                <label class="col-sm-1 col-form-label"></label>

                <div class="col-sm-1">
                    <button id="addItemBtn" type="button" class="btn btn-secondary" disabled>Add</button>
                </div>
                <div id="newTimeErrorContainer" class="col-sm-10">
                    <label id="newTimeError" hidden class="col-form-label">error text</label>
                </div>
            </div>

            <div class="form-group row customPatternGroup">
                <label class="col-sm-1 col-form-label"> </label>
                <div class="customPatternGroup col-sm-5" id="tableContainer">
                    <table id="newPatternTable" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>Day number</th>
                            <th>Time</th>
                        </tr>
                    </table>
                </div>
            </div>

                <%--      every n days and Save     --%>
            <div class="form-group row">
                <label for="everyDays" class="everyNDaysGroup col-sm-1 col-form-label">Every</label>
                <div class="everyNDaysGroup col-sm-1">
                    <select class="custom-select" type="" name="everyDays" id="everyDays">
                        <option value=2 selected>2</option>
                        <option value=3>3</option>
                        <option value=4>4</option>
                        <option value=5>5</option>
                    </select>
                </div>
                <div class="everyNDaysGroup col-sm-1">
                    <label for="everyDays" class="col-sm-1 col-form-label">days</label>
                </div>
                <label class="col-sm-1 col-form-label"> </label>
                <div class="col">
                    <button id="saveNewTimePatternBtn" class="btn btn-primary" disabled>Save</button>
                </div>
            </div>

            <div>
                <div id="calendar">
                </div>
            </div>

        </form>

    </div>

    <%--Common part start--%>
</jsp:attribute>
    <jsp:attribute name="pageScripts">
    <script src="${pageContext.request.contextPath}/static/lib/fullcalendar/core/main.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/fullcalendar/daygrid/main.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/fullcalendar/timegrid/main.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/fullcalendar/bootstrap/main.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/fullcalendar/interaction/main.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/datarangepicker/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/lib/datatables/datatables.min.js"></script>
    <script type="module" src="${pageContext.request.contextPath}/static/script/newTimePattern.js"></script>
</jsp:attribute>
    <jsp:attribute name="pageStyles">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/fullcalendar/core/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/fullcalendar/daygrid/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/fullcalendar/timegrid/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/fullcalendar/bootstrap/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/datatables/datatables.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/newTimePattern.css">
</jsp:attribute>
</t:generic-page>
<%--Common part end--%>