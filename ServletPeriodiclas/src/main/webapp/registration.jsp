<%@ include file="/fragments/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">

<head>
    <jsp:include page="fragments/head.jsp"/>
    <title >
        <fmt:message key="title.registration"/>
    </title>
</head>

<body>
<jsp:include page="fragments/header.jsp"/>
<div class="text-center" >
    <div class="sectionMainContent">
        <form class="form-signup" method="post" action="<c:url value="/registration"/>">
            <h1 class="h3 mb-3 mt-5 font-weight-normal" ><fmt:message key="form.signup"/></h1>
            <div class="container">
                <div class="row">
                    <c:if test="${errorAlreadyExist != null}">
                        <div class="alert-danger">
                            <c:set var="error" value="${errorAlreadyExist}"/>
                            <fmt:message key="${error}"/>
                        </div>
                    </c:if>
                    <div class="col">
                        <input type="text" class="form-control"
                               placeholder="<fmt:message key="form.signup.name"/>" name="name"
                               value="<c:out value="${userDTO.name}"/>" autofocus>
                    </div>
                    <div class="col">
                        <input type="text" class="form-control"
                               placeholder="<fmt:message key="form.signup.surname"/>" name="surname"
                               value="<c:out value="${userDTO.surname}"/>">
                    </div>
                </div>
                <c:if test="${errorStringName != null}">
                    <div class="alert-danger">
                        <c:set var="error" value="${errorStringName}"/>
                        <fmt:message key="${error}"/>
                    </div>
                </c:if>
                <div id="nameHelp" class="form-text" ><fmt:message key="form.signup.name.help"/></div>
                <div class="col">
                    <input type="text" class="form-control"
                           placeholder="<fmt:message key="form.signup.email"/>" name="email"
                           value="<c:out value="${userDTO.email}"/>">
                    <%--                        value="*{name}"--%>
                    <c:if test="${errorStringEmail != null}">
                        <div class="alert-danger">
                            <c:set var="error" value="${errorStringEmail}"/>
                            <fmt:message key="${error}"/>
                        </div>
                    </c:if>
                </div>
                <br>
                <div class="col">
                    <input type="password" class="form-control"
                           placeholder="<fmt:message key="form.signup.password"/>" name="password"
                           value="<c:out value="${userDTO.password}"/>">
                    <%--                        value="*{name}"--%>
                    <c:if test="${errorStringPassword != null}">
                        <div class="alert-danger">
                            <c:set var="error" value="${errorStringPassword}"/>
                            <fmt:message key="${error}"/>
                        </div>
                    </c:if>
                </div>
                <br>
                <div class="pb-4 d-flex justify-content-center">
                    <div class="custom-control custom-radio mx-2">
                        <input type="radio" checked id="customRadio1" name="role" class="custom-control-input"
                               value="READER">
                        <label class="custom-control-label" for="customRadio1">
                            <fmt:message key="radio.role.reader"/>
                        </label>
                    </div>
                    <div class="custom-control custom-radio mx-2">
                        <input type="radio" id="customRadio2" name="role" class="custom-control-input"
                               value="ADMINISTRATOR">
                        <label class="custom-control-label" for="customRadio2">
                            <fmt:message key="radio.role.administrator"/>
                        </label>
                    </div>
                </div>
                <br>
                <br>
                <div class="col">
                    <button type="submit" class="btn btn-primary" >
                        <fmt:message key="button.submit"/>
                    </button>
                </div>
            </div>
        </form>
        <a type="button" class="btn btn-danger" href="<c:url value="/login"/>">
            <fmt:message key="button.cancel"/>
        </a>
    </div>
</div>
</body>
<footer>
    <jsp:include page="fragments/scripts.jsp" />
    <jsp:include page="fragments/footer.jsp" />
</footer>
</html>
