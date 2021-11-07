<%@ include file="/fragments/taglibs.jsp"%>
<!DOCTYPE html >
<html lang="en">

<head>
    <jsp:include page="../fragments/head.jsp"/>
    <title >
        <fmt:message key="title.periodicals"/>
    </title>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="messages"/>
</head>

<body>
<jsp:include page="../fragments/header.jsp"/>
<div class="bodyContainer">


    <div class="sectionMainContent text-center">
        <c:if test="${errorNotEnoughBalance != null}">
            <div class="text-center d-flex justify-content-center mt-5">
                <div class="alert alert-danger w-50" role="alert">
                    <div>
                        <c:set value="${errorNotEnoughBalance}" var="subscription_error"/>
                        <strong><fmt:message key="${subscription_error}"/></strong>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${periodicals.hasContent()}">

            <form action="<c:url value="/periodicals"/> ">
                <div class="d-flex shadow p-3 mb-5 mt-5 bg-light rounded">
                    <div class="flex-grow-1">
                        <div class=" input-group">
                            <input type="text" class="form-control" placeholder="<fmt:message key="input.periodical.name"/>"
                                   aria-describedby="basic-addon1" name='searchQuery'
                                   value="<c:out value="${request_params.get('searchQuery')}"/>">
                            <div class="input-group-append">
                                <button class="btn btn-primary" type="submit" ><fmt:message key="button.find"/></button>
                            </div>
                        </div>
                    </div>
                    <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
                        <div class="d-grid gap-2 d-md-block" >
                            <a class="btn btn-primary" type="button"
                               style="margin-left: 25px"
                               href="<c:url value="/periodicals/add"/>"><fmt:message key="button.add"/></a>
                        </div>
                    </c:if>
                </div>
            </form>
            <div class="d-flex justify-content-center shadow p-3 bg-light rounded">
                <div>
                    <table class="table table-striped table-hover">
                        <thead class="thead-light">

<%--                        th:with="urlPrefix = '/periodicals?asc='--%>
<%--                        + ${request_params.get('asc') == null || request_params.get('asc') ? 'false':'true'}"--%>
                        <tr >
                            <c:set var="url" value='/periodicals?asc='/>
                            <c:choose>
                                <c:when test="${request_params.get('asc') == null || request_params.get('asc') == 'true'}">
                                    <c:set var="urlSort" value="${url}false" />
                                </c:when>
                                <c:when test="${request_params.get('asc') == 'false'}">
                                    <c:set var="urlSort" value="${url}true" />
                                </c:when>
                            </c:choose>

                            <c:if test="${sessionScope.role == 'READER'}">
                                <th style="width: 75px"></th>
                            </c:if>
                            <div class="list-group">
                                <th ><a class="list-group-item list-group-item-action list-group-item-primary"
                                        href="<c:url value="${urlSort}"><c:param name="sortField" value="p_name"/></c:url>"
                                        ><fmt:message key="table.periodicals.name"/></a></th>
                                <th><a class="list-group-item list-group-item-action list-group-item-primary"
                                       href="<c:url value="${urlSort}"><c:param name="sortField" value="p_price"/></c:url>"
                                       ><fmt:message key="table.periodicals.price"/></a></th>
                                <th>
                                    <div class="list-group-item list-group-item-action list-group-item-primary">
                                        <ul class="navbar-nav">
                                            <li class="nav-item dropdown">
                                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
                                                   role="button" data-bs-toggle="dropdown" aria-expanded="false"
                                                   style="padding: 0"
                                                   ><fmt:message key="table.periodicals.subject"/>
                                                </a>
                                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                                    <li><a class="dropdown-item"
                                                           href="<c:url value="${urlSort}"><c:param name="subject" value="NEWS"/></c:url>"
                                                           ><fmt:message key="table.periodicals.subject.news"/></a></li>
                                                    <li><a class="dropdown-item"
                                                           href="<c:url value="${urlSort}"><c:param name="subject" value="SPORT"/></c:url>"
                                                           ><fmt:message key="table.periodicals.subject.sport"/></a></li>
                                                    <li><a class="dropdown-item"
                                                           href="<c:url value="${urlSort}"><c:param name="subject" value="SCIENCE"/></c:url>"
                                                           ><fmt:message key="table.periodicals.subject.science"/></a></li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                </th>
                                <th><a class="list-group-item list-group-item-action list-group-item-primary"
                                       href="<c:url value="${urlSort}"><c:param name="sortField" value="p_subscribers"/></c:url>"
                                       ><fmt:message key="table.periodicals.subscribers"/></a></th>
                            </div>
                        </tr>
                        </thead>
                        <tbody >
                        <c:forEach items="${periodicals.getItems()}" var="periodical">
                            <tr scope="row" >
                                <c:if test="${sessionScope.role == 'READER'}">
                                    <td >
                                        <form class="form-signup"
                                              method="post"
                                              action="<c:url value="/periodicals/${periodical.id}/subscription"/>">
                                            <c:if test="${user_subscribe.get(periodical.name) == true}">
                                                <button type="submit" class="btn btn-success">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmark-check-fill" viewBox="0 0 16 16">
                                                        <path fill-rule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zm8.854-9.646a.5.5 0 0 0-.708-.708L7.5 7.793 6.354 6.646a.5.5 0 1 0-.708.708l1.5 1.5a.5.5 0 0 0 .708 0l3-3z"/>
                                                    </svg>
                                                </button>
                                            </c:if>
                                            <c:if test="${user_subscribe.get(periodical.name) == false}">
                                                <button type="submit" class="btn btn-secondary">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmark" viewBox="0 0 16 16">
                                                        <path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5V2zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1H4z"/>
                                                    </svg>
                                                </button>
                                            </c:if>
                                        </form>
                                    </td>
                                </c:if>
                                <td  class="align-middle">
                                    <a class="alert-link"
                                       href="<c:url value="/periodicals/${periodical.id}"/>"
                                       ><c:out value="${periodical.name}"/></a>
                                </td>
                                <td  class="align-middle">
                                    <div  ><c:out value="${periodical.price}"/></div>
                                </td>

                                <td  class="align-middle">
                                    <div  ><c:out value="${periodical.subject}"/></div>
                                </td>
                                <td  class="align-middle">
                                    <div  ><c:out value="${periodical.subscribers}"/></div>
                                </td>
                            </tr>

                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        <c:if test="${periodicals.hasContent()}">
            <div class="m-xl-5 d-flex justify-content-center">
                <div>
                    <nav><c:set var="url" value='/periodicals?asc='/>
                        <ul class="pagination flex-wrap">
                            <c:if test="${request_params.get('asc') != null}">
                                <c:set var="urlPagin" value="${url}${request_params.get('asc')}" />
                            </c:if>
                            <c:if test="${request_params.get('sortField') != null && request_params.get('sortField') != ''}">
                                <c:set var="urlPagin" value="${urlPagin}&sortField=${request_params.get('sortField')}" />
                            </c:if>
                            <c:if test="${request_params.get('subject') != null && request_params.get('subject') != ''}">
                                <c:set var="urlPagin" value="${urlPagin}&subject=${request_params.get('subject')}" />
                            </c:if>
                            <c:if test="${request_params.get('searchQuery') != null && request_params.get('searchQuery') != ''}">
                                <c:set var="urlPagin" value="${urlPagin}&searchQuery=${request_params.get('searchQuery')}" />
                            </c:if>
                            <c:forEach begin="1" end="${periodicals.getTotalPages()}" step="1"
                                       var="i" varStatus="<string>">
                                <li
                                    class="${periodicals.getNumber() != i ? 'page-item' : 'page-item active'}">
                                    <a href="<c:url value="${urlPagin}">
											<c:param name="page" value="${i}"/>
										</c:url>" class="page-link"><c:out value = "${i}"/></a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </div>
        </c:if>
        <c:if test="${(!periodicals.hasContent()) && (errorNotEnoughBalance == null) }">
            <h3 class="m-xl-5 d-flex justify-content-center"
                > <fmt:message key="periodicals.not.found"/> </h3>
        </c:if>
    </div>
</div>

</body>
<footer>
    <jsp:include page="../fragments/scripts.jsp" />
    <jsp:include page="../fragments/footer.jsp" />
</footer>
</html>
