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
<div class="text-center" >
    <div class="sectionMainContent">
        <form class="form-signup" method="POST" action="<c:url value="/profile/replenishment"/>">
            <h1 class="h3 mb-3 mt-5 font-weight-normal" >
                <fmt:message key="form.replenishment"/>
            </h1>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <input type="number" class="form-control" aria-describedby="numberHelp"
                               placeholder="<fmt:message key="form.profile.replenishment"/>"
                               name="value" value="value"
                               min="1" max="1000">
                        <c:if test="${errorStringNumbers != null}">
                            <div class="alert-danger">
                                <c:set var="error" value="${errorStringNumbers}"/>
                                <fmt:message key="${error}"/>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <br>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="button.confirm"/>
            </button>
        </form>
        <a type="button" class="btn btn-danger" href="<c:url value="/profile"/>">
            <fmt:message key="button.cancel"/>
        </a>
    </div>
</div>

</body>
<footer>
    <jsp:include page="../fragments/scripts.jsp" />
    <jsp:include page="../fragments/footer.jsp" />
</footer>
</html>
