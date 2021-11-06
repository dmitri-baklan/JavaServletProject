<%@ include file="/fragments/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en" >

<head>
    <jsp:include page="fragments/head.jsp"/>
    <jsp:include page="fragments/header.jsp"/>


    <title >
        <fmt:message key="title.welcome"/>
    </title>
</head>

<header>

</header>

<div class="jumbotron">
    <div class="container">
        <h3 class="display-4" ><fmt:message key="page.welcome.greeting"/> </h3>
        <p ><fmt:message key="page.welcome.greeting.subtitle"/></p>
        <p>
            <c:if test="${sessionScope.role == 'GUEST'}">
                <a class="btn btn-primary btn-lg" href='<c:url value="/login"/>'>
                    <fmt:message key="page.welcome.button.work"/> &raquo;
                </a>
            </c:if>
            <c:if test="${sessionScope.role == 'ADMINISTRATOR' || sessionScope.role == 'READER'}">
                <a class="btn btn-primary btn-lg" href='<c:url value="/periodicals"/>' >
                        <fmt:message key="page.welcome.button.work"/> &raquo;
                </a>
            </c:if>


        </p>
    </div>
</div>
<footer>
    <jsp:include page="fragments/scripts.jsp" />
    <jsp:include page="fragments/footer.jsp" />
</footer>

</body>

</html>