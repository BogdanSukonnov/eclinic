<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <jsp:include page="head.jsp"/>
    <body>
        <jsp:include page="header.jsp"/>

        <h1>Patients</h1>
        <div class="container-fluid">
            <table class="table">
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Status</th>
                    <th scope="col">Insurance</th>
                </tr>
                <c:forEach items="${patients}" var="patient" varStatus="status">
                    <tr>
                        <td>${patient.fullName}</td>
                        <td>${patient.patientStatus}</td>
                        <td>${patient.insuranceNumber}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <%--    <h1>Patients</h1>--%>
        <%--    <form action="/newPatient" method="post">--%>
        <%--        <label>Full name:--%>
        <%--            <input type="text" name="fullName"/>--%>
        <%--        </label><br>--%>
        <%--        <input type="submit" />--%>
        <%--    </form>--%>

        <jsp:include page="scripts.jsp"/>
    </body>
</html>
