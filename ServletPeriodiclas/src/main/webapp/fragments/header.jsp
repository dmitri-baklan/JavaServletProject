<%@ include file="taglibs.jsp" %>
<!-- Bootstrap CSS -->
<%--<body>--%>
<%-- TODO: favicon.ico redirection--%>
<link rel="icon" href="#">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
<%--        <c:url value="/welcome">--%>
        <a class="navbar-brand" href="<c:url value="/welcome"/>">Periodicals</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDarkDropdown"
                aria-controls="navbarNavDarkDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDarkDropdown">
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink"
                       role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="nav.link.lang" />
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
                        <li><a class="dropdown-item" href="?lang=en">
                            <fmt:message key="nav.language.english" /></a></li>
                        <li><a class="dropdown-item" href="?lang=ua">
                            <fmt:message key="nav.language.ukrainian" />
                        </a></li>
<%--                        TODO:TEST!--%>
<%--                        <li>--%>
<%--&lt;%&ndash;                            <a class="dropdown-item" href="#">&ndash;%&gt;--%>
<%--                            <c:set scope="request" var="testc" value="${test}" />--%>

<%--                            <fmt:message key="${testc}"/>--%>
<%--&lt;%&ndash;                        </a>&ndash;%&gt;--%>
<%--                        </li>--%>
<%--                        <li>--%>
<%--                            <c:out value="${testc}" />--%>
<%--                        </li>--%>

                    </ul>
                </li>
                <c:if test="${sessionScope.role == 'ADMINISTRATOR' || sessionScope.role == 'READER'}">
                    <li class="nav-item" >
                        <a class="nav-link" href="<c:url value="/periodicals"/>" >

                            <fmt:message key="nav.link.periodicals" />
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
                    <li class="nav-item" >
                        <a class="nav-link" href="<c:url value="/profile/readers"/>" >
                            <fmt:message key="nav.link.readers" />
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.role == 'READER'}">
                    <li class="nav-item" >
                        <a class="nav-link" href="<c:url value="/profile/replenishments"/>" >
                            <fmt:message key="nav.link.replenishments" />
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>
        <c:if test="${sessionScope.role == 'ADMINISTRATOR' || sessionScope.role == 'READER'}">
            <div class="form-inline my-2 my-lg-0" >
                <div class="navbar-nav mr-auto">
                    <div class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class=" mr-1">
                                    <c:out value="${sessionScope.email}"/>
<%--                                <fmt:message key="${sessionScope.email}"/>--%>
                                </span>
                            <c:if test="${sessionScope.role == 'READER'}">
                                    <span class="badge bg-success">
                                        <fmt:message key="role.reader" /></span>
                            </c:if>
                            <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
                                    <span class="badge bg-primary">
                                        <fmt:message key="role.administrator" />
                                    </span>
                            </c:if>

                            <span class="mr-1">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                              <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                                            </svg>
                                </span>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="<c:url value="/profile"/>" >
                                <fmt:message key="nav.link.profile" />
                            </a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="<c:url value="/logout"/>" >
                                <fmt:message key="nav.link.logout" />
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</nav>