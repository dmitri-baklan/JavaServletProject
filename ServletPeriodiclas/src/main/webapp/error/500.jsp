<%@ include file="/fragments/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">

<head>
    <jsp:include page="../fragments/head.jsp"/>
    <title >
        <fmt:message key="title.error.500"/>
    </title>
</head>

<body>
<jsp:include page="../fragments/header.jsp"/>
<div class="text-center d-flex justify-content-center mt-5">
    <div class="alert alert-danger w-50" role="alert">
        <strong >
            <fmt:message key="error.500"/>
        </strong>
        <snap >
            <fmt:message key="error.500.message"/>
        </snap>
    </div>
</div>
</body>
<footer>
    <jsp:include page="../fragments/scripts.jsp" />
    <jsp:include page="../fragments/footer.jsp" />
</footer>
</html>