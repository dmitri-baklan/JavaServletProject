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
        <form class="form-signup" method="post" action="<c:url value="/profile/edit"/>">
            <h1 class="h3 mb-3 mt-5 font-weight-normal" ><fmt:message key="form.profile.edit"/></h1>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <input type="text" class="form-control"
                               placeholder="<fmt:message key="form.signup.name"/>" name="name"
                               value="<c:out value="${userDTO.name}"/>">
                    </div>
                    <div class="col">
                        <input type="text" class="form-control"
                               placeholder="<fmt:message key="form.signup.surname"/>" name="surname"
                               value="<c:out value="${userDTO.surname}"/>">
                </div>
                    <c:if test="${errorStringName != null}">
                        <div class="alert-danger">
                            <c:set var="error" value="${errorStringName}"/>
                            <fmt:message key="${error}"/>
                        </div>
                    </c:if>
                </div>
                <div id="nameHelp" class="form-text" >
                    <fmt:message key="form.signup.name.help"/>
                </div>
                <div class="row">
                    <p class="fs-2" ><c:out value="${userDTO.email}"/></p>
                    <input type="text" class="form-control"
                           placeholder="<fmt:message key="form.signup.email"/>" name="email"
                           value="<c:out value="${userDTO.email}"/>"
                    hidden="true">
                </div>
                <br>
                <div class="col">
                    <input type="password" class="form-control"
                           placeholder="<fmt:message key="form.signup.password"/>" name="password"
                           value="<c:out value="${userDTO.password}"/>">
                    <c:if test="${errorStringPassword != null}">
                        <div class="alert-danger">
                            <c:set var="error" value="${errorStringPassword}"/>
                            <fmt:message key="${error}"/>
                        </div>
                    </c:if>
                </div>
                <div class="col">
                    <button type="submit" class="btn btn-primary">
                        <fmt:message key="button.submit"/>
                    </button>
                </div>
            </div>
        </form>
        <a type="submit" class="btn btn-danger" href="<c:url value="/profile"/>">
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
