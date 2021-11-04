<%@ include file="/fragments/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">

<head>
    <jsp:include page="fragments/head.jsp"/>
    <title >
        <fmt:message key="title.login"/>
    </title>
</head>

<body>
    <jsp:include page="fragments/header.jsp"/>
    <div class="text-center" >
        <div class="sectionMainContent">
            <form class="form-signin" action="<c:url value="/login"/>" method="post">
                <p class="fs-3">
                    <fmt:message key="form.signin"/>
                </p>
                <c:if test="${errorBlankField != null}">
                    <div class="alert alert-danger">
                        <c:set value="${errorBlankField}" var="login_error"/>
                        <fmt:message key="${login_error}"/>
                    </div>
                </c:if>


                <div class="mb-3">
                    <input type="email" class="form-control" id="exampleInputEmail1"
                           placeholder="<fmt:message key="form.signin.email"/>" name="email">
                    <div id="emailHelp" class="form-text"></div>
                </div>
                <div class="mb-3">
                    <input type="password" class="form-control" id="exampleInputPassword1"
                           placeholder="<fmt:message key="form.signin.password"/>" name="password">
                </div>
                <a href="<c:url value="/registration"/> " class="link-primary" id="signUpLink"
                   ><fmt:message key="form.link.signup"/></a>
                <br/>
                <br/>
                <button type="submit" class="btn btn-primary" ><fmt:message key="button.submit"/></button>
            </form>
            <br>

            <a href="<c:url value="/welcome"/>" class="btn btn-primary"><fmt:message key="form.signin.unauthorized"/></a>
        </div>
    </div>
</body>
<footer>
    <jsp:include page="fragments/scripts.jsp" />
    <jsp:include page="fragments/footer.jsp" />
</footer>

</html>