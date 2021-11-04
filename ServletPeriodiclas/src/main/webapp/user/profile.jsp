<%@ include file="/fragments/taglibs.jsp"%>
<head>
    <%--    <meta charset="UTF-8">--%>
    <jsp:include page="../fragments/head.jsp"/>
<%--    <link rel="icon" href="#">--%>

    <title >
        <fmt:message key="title.profile"/>
    </title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"/>
<div class="sectionMainContent">
    <br>
    <br>
    <div class="container">
        <div class="row">
            <div class="col-6 col-sm-3">
                <svg xmlns="http://www.w3.org/2000/svg" width="200" height="200" fill="currentColor" class="bi bi-person-square" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm12 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1v-1c0-1-1-4-6-4s-6 3-6 4v1a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12z"/>
                </svg>
                <br>
                <br>
                <c:if test="${sessionScope.role == 'READER'}">
                    <div>
                        <span class="fs-5" ><fmt:message key="page.profile.balance" /></span>
                        <span  class="fs-5" ><c:out value="${user.balance}"/> </span>
                        <br>
                        <a type="button" class="btn btn-outline-primary"
                           href="<c:url value="/profile/replenishment"/>"><fmt:message key="button.replenishment" /></a>

                    </div>
                </c:if>
            </div>
            <div class="col-6 col-sm-3">
                <h1><span >
                    <c:out value="${user.name} ${user.surname}"/>
                </span></h1>
                <a href="<c:url value="/profile/edit"/>">
                    <span class="fs-7" ><fmt:message key="page.profile.edit" /></span>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                    </svg>
                </a>
                <p class="fs-2"><c:out value="${user.email}"/></p>
                <div class="fs-3">
                    <c:if test="${sessionScope.role == 'READER'}">
                        <span class="badge bg-success">
                            <fmt:message key="role.reader" />
                        </span>
                    </c:if>
                    <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
                        <span class="badge bg-primary">
                            <fmt:message key="role.administrator" />
                        </span>
                    </c:if>
                    <c:if test="${sessionScope.role == 'READER'}">
                        <p>
                            <span class="fs-5" ><fmt:message key="page.profile.subscriptions"/></span>
                            <span class="fs-5" ><c:out value="${user.subscriptions}"/></span>
                        </p>
                    </c:if>
                </div>
            </div>

            <div class="col">
                <c:if test="${sessionScope.role == 'READER'}">
                    <p class="fs-3" ><fmt:message key="page.profile.periodical.list" /></p>
                    <table class="table table-borderless table-hover table-scroll">
                        <thead class="thead-light">
                        <tr align="center">
                            <div class="list-group" >
                                <th >
                                    <a class="list-group-item list-group-item-action list-group-item-primary">
                                    <fmt:message key="table.profile.periodicals.name" />
                                    </a>
                                </th>
                                <th><a class="list-group-item list-group-item-action list-group-item-primary">
                                    <fmt:message key="table.profile.periodical.cost" /></a></th>

                            </div>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="periodical" items="${user.periodicals}">
                                <tr scope="row" align="center">
                                    <td class="titleColumn" >
                                        <a class="alert-link"
                                           href="<c:url value="/periodicals/${periodical.id}"/>">
                                            <c:out value="${periodical.name}"/>
                                        </a>
                                    </td>
                                    <td>
                                        <c:out value="${periodical.price}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </c:if>
            </div>
        </div>
        <div class="row" >
            <div class="col">

            </div>
        </div>
    </div>
</div>



</body>
<footer>
    <jsp:include page="../fragments/scripts.jsp" />
    <jsp:include page="../fragments/footer.jsp" />
</footer>
</html>
