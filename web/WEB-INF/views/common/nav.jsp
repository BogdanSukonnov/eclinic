<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="main-nav navbar navbar-expand-lg navbar-dark">
    <sec:authorize access="isAuthenticated()">
        <div class="navbar-nav dropdown">
            <a class="nav-link dropdown-toggle" href="" id="UserDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <sec:authentication property="principal.fullName" />
            </a>
            <div class="dropdown-menu" aria-labelledby="UserDropdown">
                <a class="dropdown-item" href="${pageContext.request.contextPath}/login/sign-out">Logout</a>
            </div>
        </div>
        <div class="navbar-nav">
            <button class="nav-item nav-link">Patients</button>
            <button class="nav-item nav-link">Precsriptions</button>
        </div>
    </sec:authorize>

    <sec:authorize access="!isAuthenticated()">
        <div class="navbar-nav">
            <div class="nav-item nav-link">Not Authorized</div>
        </div>
    </sec:authorize>
</nav>