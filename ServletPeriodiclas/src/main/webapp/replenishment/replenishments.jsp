<%@ include file="/fragments/taglibs.jsp"%>
<%@taglib uri="http://example.com/functions" prefix="f" %>
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
        <div class="container" align="center">
            <div class="col-6" >
                <br>
                <c:if test="${replenishments.size() > 0 }">
                    <div class="d-flex justify-content-center shadow p-3 bg-light rounded">
                        <div>
                            <div class="overflow-auto" style="height: 500px">
                                <table class="table table-striped table-hover">
                                    <thead class="thead-light">
                                    <tr align="center">
                                        <div class="list-group">
                                            <th>
                                                <div class="list-group-item list-group-item-action list-group-item-primary"
                                                     >
                                                    <fmt:message key="table.replenishments.amount"/>
                                                </div></th>
                                            <th >
                                                <div class="list-group-item list-group-item-action list-group-item-primary"
                                                     >
                                                    <fmt:message key="table.replenishments.time"/>
                                                </div></th>
                                        </div>
                                    </tr>

                                    </thead>
                                    <tbody>
                                    <c:forEach var="replenishment" items="${replenishments}">
                                        <tr  align="center">

                                            <td class="align-middle">
                                                <c:out value="${replenishment.getSum()}"/>
                                            </td>
                                            <td class="align-middle">
<%--                                                <c:out value="${replenishment.getTime()}"/>--%>
                                                <c:out value="${f:formatLocalDateTime(replenishment.getTime(), 'dd.MM.yyyy HH:mm:ss')}" />
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>
                </c:if>
                <c:if test="${!(replenishments.size() > 0)}">
                    <h3 class="m-xl-5 d-flex justify-content-center">
                        <fmt:message key="replenishments.not.found"/>
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
