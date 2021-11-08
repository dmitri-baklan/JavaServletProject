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
<div class="bodyContainer">
    <div class="sectionMainContent text-center">
        <div class="container" align="center">
            <c:if test="${errorNotFound != null}">
                <div class="text-center d-flex justify-content-center mt-5">
                    <div class="alert alert-danger w-50" role="alert">
                        <div>
                            <c:set value="${errorNotFound}" var="not_found_error"/>
                            <strong><fmt:message key="${not_found_error}"/></strong>
                        </div>
                    </div>
                </div>
            </c:if>
            <div class="col-11" >
                <form action="<c:url value="/profile/readers"/> ">
                    <div class="d-flex shadow p-3 mb-5 mt-5 bg-light rounded">

                        <div class="flex-grow-1">
                            <div class=" input-group">
                                <input type="text" class="form-control"
                                       placeholder="<fmt:message key="input.user.email"/> "
                                       aria-describedby="basic-addon1" name='searchQuery'
                                       value="<c:out value="${request_params.get('searchQuery')}"/>">
                                <div class="input-group-append">
                                    <button class="btn btn-primary" type="submit" >
                                        <fmt:message key="button.find"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
                <c:if test="${readers.hasContent()}">
                    <div class="d-flex justify-content-center shadow p-3 bg-light rounded">

                        <div>
                            <table class="table table-striped table-hover">
                                <thead class="thead-light">

                                <tr th:with="urlPrefix = '/profile/readers?'" align="center">
                                    <c:set var="urlPrefix" value="/profile/readers?"/>
                                    <div class="list-group">
                                        <th style="width: 150px"></th>
                                        <th>
                                            <div class="list-group-item list-group-item-action list-group-item-primary"
                                                 >
                                                <fmt:message key="table.readers.email"/>
                                            </div></th>
                                        <th >
                                            <div class="list-group-item list-group-item-action list-group-item-primary"
                                                 >
                                                <fmt:message key="table.readers.name.and.surname"/>
                                            </div></th>
                                        <th style="width: 200px">
                                            <div class="list-group-item list-group-item-action list-group-item-primary">
                                                <fmt:message key="table.readers.subscription"/>
                                            </div></th>
                                        <th style="width: 100px">
                                            <div class="list-group-item list-group-item-action list-group-item-primary"
                                                 >
                                                <fmt:message key="table.readers.balance"/>
                                            </div></th>

                                        <th style="width: 100px">
                                            <div class="list-group-item list-group-item-action list-group-item-primary"
                                                 >
                                                <fmt:message key="table.readers.status"/>
                                            </div></th>

                                    </div>
                                </tr>

                                </thead>
                                <tbody>
                                    <c:forEach var="reader" items="${readers.items}">
                                        <tr  align="center">
                                            <form method="POST" action="<c:url value="/profile/readers/${reader.id}"/> ">
                                                <c:if test="${reader.isActive()}">
                                                    <td class="align-middle">
                                                        <button type="submit" class="btn btn-warning">
                                                            <div>
                                                                <fmt:message key="table.readers.status.block"/>
                                                            </div>
                                                        </button>
                                                    </td>
                                                </c:if>
                                                <c:if test="${!reader.isActive()}">
                                                    <td class="align-middle">
                                                        <button type="submit" class="btn btn-secondary">
                                                            <div>
                                                                <fmt:message key="table.readers.status.unblock"/>
                                                            </div>
                                                        </button>
                                                    </td>
                                                </c:if>
                                                <td class="align-middle">
                                                    <c:out value="${reader.email}"/>
                                                </td>
                                                <td class="align-middle">
                                                    <c:out value="${reader.name} ${reader.surname}"/>
                                                </td>
                                                <td th:text="${reader.periodicals.size()}"
                                                    class="align-middle">
                                                    <c:out value="${reader.subscriptions}"/>
                                                </td>
                                                <td class="align-middle">
                                                    <c:out value="${reader.balance}"/>
                                                </td>
                                                <td class="align-middle">
                                                    <c:if   test="${reader.isActive()}">
                                                        <div class="badge rounded-pill bg-success">
                                                            <fmt:message key="table.readers.status.active"/>
                                                        </div>
                                                    </c:if>
                                                    <c:if   test="${!reader.isActive()}">
                                                        <div class="badge rounded-pill bg-secondary">
                                                            <fmt:message key="table.readers.status.notactive"/>
                                                        </div>
                                                    </c:if>
                                                </td>
                                            </form>
                                        </tr>
                                    </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
                <c:if test="${readers.hasContent()}">
                    <div class="m-xl-5 d-flex justify-content-center">
                        <div>
                            <nav>
                                <ul class="pagination flex-wrap" >
                                    <c:forEach begin="1" end="${readers.getTotalPages()}" step="1"
                                               var="i" varStatus="<string>"
                                    >
                                        <li
                                                class="${readers.getNumber() != i ? 'page-item' : 'page-item active'}">
                                            <a href="<c:url value="${urlPrefix}">
											<c:param name="page" value="${i}"/>
										</c:url>" class="page-link"><c:out value = "${i}"/></a>
                                        </li>
                                        <%--                            items="<object>"--%>
                                    </c:forEach>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </c:if>
                <c:if test="!readers.hasContent()}">
                    <h3 class="m-xl-5 d-flex justify-content-center">
                        <fmt:message key="readers.not.found"/>
                    </h3>
                </c:if>
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
