<%@ include file="/fragments/taglibs.jsp"%>
<!DOCTYPE html >
<html lang="en">

<head>
    <jsp:include page="../fragments/head.jsp"/>
    <title >
        <fmt:message key="title.periodical.add"/>
    </title>
</head>

<body>
<jsp:include page="../fragments/header.jsp"/>
<div class="text-center" >
    <div class="sectionMainContent">
        <form class="form-signup" method="post" action="<c:url value="/periodicals/${periodical.id}/edit"/>">
            <h1 class="h3 mb-3 mt-5 font-weight-normal" ><fmt:message key="form.periodical.edit"/> </h1>
            <div class="container">
                <div class="row">
                    <div class="col">
                        <input type="text" class="form-control"
                               placeholder="<fmt:message key="form.periodical.name"/>" name="name"
                               value="<c:out value="${periodical.name}"/>"
                               autofocus>
                        <c:if test="${errorStringName != null}">
                            <div class="alert-danger">
                                <c:set var="error" value="${errorStringName}"/>
                                <fmt:message key="${error}"/>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div id="nameHelp" class="form-text">
                    <fmt:message key="form.periodical.name.help"/>
                </div>
                <div class="col">
                    <input type="number" class="form-control" aria-describedby="numberHelp"
                           placeholder="<fmt:message key="form.periodical.price"/>" name="price"
                           min="1" max="1000"
                           value="<c:out value="${periodical.price}"/>">
                    <c:if test="${errorBlankField != null}">
                        <div class="alert-danger">
                            <c:set var="error" value="${errorBlankField}"/>
                            <fmt:message key="${error}"/>
                        </div>
                    </c:if>
                </div>
                <br>
                <div class="col">
                    <div class="text-nowrap bd-highlight">
                        <fmt:message key="form.periodical.subject"/>
                    </div>
                </div>
                <br>
                <div class="pb-4 d-flex justify-content-center">
                    <div class="custom-control custom-radio mx-2">
                        <input type="radio" checked id="customRadio1" name="subject" class="custom-control-input"
                               value="NEWS">
                        <label class="custom-control-label" for="customRadio1">
                            <fmt:message key="form.periodical.subject.news"/>
                        </label>
                    </div>
                    <div class="custom-control custom-radio mx-2">
                        <input type="radio" id="customRadio2" name="subject" class="custom-control-input"
                               value="SPORT" field="*{subject}">
                        <label class="custom-control-label" for="customRadio2">
                            <fmt:message key="form.periodical.subject.sport"/>
                        </label>
                    </div>
                    <div class="custom-control custom-radio mx-2">
                        <input type="radio" id="customRadio3" name="subject" class="custom-control-input"
                               value="SCIENCE" >
                        <label class="custom-control-label" for="customRadio2">
                            <fmt:message key="form.periodical.subject.science"/>
                        </label>
                    </div>
                </div>
                <br>
                <div class="col">
                    <span class="text-nowrap bd-highlight"><
                        fmt:message key="page.periodical.subscribers"/>
                    </span>
                    <span >
                        <c:out value="${periodical.subscribers}"/>
                    </span>
                </div>
                <br>
                <div class="col">
                    <button type="submit" class="btn btn-primary" >
                        <fmt:message key="button.submit"/>
                    </button>
                </div>
            </div>
        </form>
        <a type="button" class="btn btn-danger" href="<c:url value="/periodicals/${periodical.id}"/>">
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
