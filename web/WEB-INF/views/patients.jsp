<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- flavicon -->
        <link rel="apple-touch-icon" sizes="180x180" href="${pageContext.request.contextPath}/static/images/favicon-package/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/static/images/favicon-package/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/static/images/favicon-package/favicon-16x16.png">
        <link rel="manifest" href="${pageContext.request.contextPath}/static/images/favicon-package/site.webmanifest">
        <link rel="mask-icon" href="${pageContext.request.contextPath}/static/images/favicon-package/safari-pinned-tab.svg" color="#5bbad5">
        <meta name="msapplication-TileColor" content="#da532c">
        <meta name="theme-color" content="#ffffff">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- app css       -->
        <link rel="stylesheet" href="<c:url value="/static/css/eclinic.css" />">

        <title>patients</title>
    </head>
    <body>
        <header class="navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar">
            <img src="<c:url value="/static/images/logo.svg" />" alt="TestDisplay" height="36" width="64"/>
            <a class="navbar-brand" href="/" aria-label="Home"><svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" class="d-block" viewBox="0 0 612 612" role="img" focusable="false"><title>Home</title></svg></a>

        </header>
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

        <!-- Bootstrap JS: jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
