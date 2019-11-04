<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav id="main-nav" class="main-nav navbar navbar-expand-lg navbar-dark">
    <sec:authorize access="isAuthenticated()">
        <div class="navbar-nav dropdown">
            <a id="UserDropdown" class="nav-link dropdown-toggle" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <sec:authentication property="principal.fullName" />
            </a>
            <div id="logout" class="dropdown-menu" aria-labelledby="UserDropdown">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/login/sign-out">Logout</a>
            </div>
        </div>
        <div class="navbar-nav">
            <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
                <button class="nav-item nav-link" onclick="window.location.href = '/doctor/patients';">Patients</button>
                <button class="nav-item nav-link" onclick="window.location.href = '/doctor/prescriptions';">Prescriptions</button>
                <button class="nav-item nav-link" onclick="window.location.href = '/doctor/treatments';">Treatments</button>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')">
                <button class="nav-item nav-link" onclick="window.location.href = '/nurse/events';">Events</button>
            </sec:authorize>
        </div>
    </sec:authorize>

    <sec:authorize access="!isAuthenticated()">
        <div class="navbar-nav">
            <div id="NotAuthorized" class="nav-link">Not Authorized</div>
        </div>
    </sec:authorize>
</nav>