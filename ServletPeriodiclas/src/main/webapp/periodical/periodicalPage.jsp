<%@ include file="/fragments/taglibs.jsp"%>
<%@page pageEncoding="UTF-8"%>
<%--[<%@ page import="java.net.URLEncoder"%>--%>
<%--<%response.sendRedirect("targetPage.jsp?MyParam="+URLEncoder.encode("Русский текст","cp1251"));%>]--%>
<!DOCTYPE html >
<html lang="en">

<head>
    <jsp:include page="../fragments/head.jsp"/>
    <title >
        <fmt:message key="title.periodicals"/>
    </title>
<%--    <fmt:setLocale value="${sessionScope.locale}"/>--%>
<%--    <fmt:setBundle basename="messages"/>--%>
</head>

<body>
<jsp:include page="../fragments/header.jsp"/>
<div class="sectionMainContent">
    <br>
    <br>
    <div class="container">
        <div class="row">
            <div class="col-6 col-sm-3">
                <svg xmlns="http://www.w3.org/2000/svg" width="200" height="200" fill="currentColor" class="bi bi-newspaper" viewBox="0 0 16 16">
                    <path d="M0 2.5A1.5 1.5 0 0 1 1.5 1h11A1.5 1.5 0 0 1 14 2.5v10.528c0 .3-.05.654-.238.972h.738a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 1 1 0v9a1.5 1.5 0 0 1-1.5 1.5H1.497A1.497 1.497 0 0 1 0 13.5v-11zM12 14c.37 0 .654-.211.853-.441.092-.106.147-.279.147-.531V2.5a.5.5 0 0 0-.5-.5h-11a.5.5 0 0 0-.5.5v11c0 .278.223.5.497.5H12z"/>
                    <path d="M2 3h10v2H2V3zm0 3h4v3H2V6zm0 4h4v1H2v-1zm0 2h4v1H2v-1zm5-6h2v1H7V6zm3 0h2v1h-2V6zM7 8h2v1H7V8zm3 0h2v1h-2V8zm-3 2h2v1H7v-1zm3 0h2v1h-2v-1zm-3 2h2v1H7v-1zm3 0h2v1h-2v-1z"/>
                </svg>
            </div>
            <div class="col-6 col-sm-3">
                <h1><span > <c:out value="${periodical.getName()}"/>
                </span></h1>
                <c:if   test="${sessionScope.role == 'ADMINISTRATOR'}">
                    <a href="<c:url value="/periodicals/${periodical.getId()}/edit"/> "
                       >
                        <span class="fs-7"  ><fmt:message key="page.periodical.edit" /></span>
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                            <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                        </svg>
                    </a>
                </c:if>
                <p class="fs-2" ><c:out value="${periodical.subject}"/></p>
                <span class="fs-5" ><fmt:message key="page.periodical.price" /></span>
                <span class="fs-5" ><c:out value="${periodical.price}"/></span>
                <p></p>
                <span class="fs-5" ><fmt:message key="page.periodical.subscribers" /></span>
                <span class="fs-5" ><c:out value="${periodical.subscribers}"/></span>
            </div>
            <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
                <div class="col" >
                    <p class="fs-3" ><fmt:message key="page.periodical.users.list" /></p>
                    <div class="overflow-auto" style="height: 200px">
                        <table class="table table-borderless table-hover table-scroll">
                            <thead class="thead-light">
                            <tr align="center">
                                <div class="list-group" >
                                    <th ><a class="list-group-item list-group-item-action list-group-item-primary"
                                    ><fmt:message key="table.periodical.users.login" /></a></th>
                                    <th><a class="list-group-item list-group-item-action list-group-item-primary"
                                    >
                                        <fmt:message key="table.periodical.users.name.and.surname" />
                                    </a></th>

                                </div>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr scope="row"  align="center">

                                    <td class="titleColumn" >
                                        <c:out value="${user.email}"/>
                                    </td>
                                    <td >
                                        <c:out value="${user.name} ${user.surname}"/>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>

        </div>
        <div class="row">
            <c:if test="${sessionScope.role == 'ADMINISTRATOR'}">
                <div class="col-6 col-sm-4" >
                    <form method="POST" action="<c:url value="/periodicals/${periodical.id}/delete"/> " >
                        <button type="submit" class="btn btn-outline-danger"
                                value="Delete"><fmt:message key="button.delete"/> </button>
                    </form>
                </div>
            </c:if>

        </div>
    </div>
</div>

</body>
<footer>
    <jsp:include page="../fragments/scripts.jsp" />
    <jsp:include page="../fragments/footer.jsp" />
</footer>
</html>
