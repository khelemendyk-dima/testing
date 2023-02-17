<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="customtags" prefix="ctg"%>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="profile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">

    <h2 class="mb-2"><ctg:hello role="${sessionScope.role}" locale="${sessionScope.locale}"/></h2>
    <h3 class="mb-3"><fmt:message key="profile.info"/>:</h3>
    <div class="ms-4 fs-5">
        <p class="fw-bold fs-4 mb-0"><fmt:message key="email"/></p>
        <p>${sessionScope.loggedUser.email}</p>
        <p class="fw-bold fs-4 mb-0"><fmt:message key="name"/></p>
        <p>${sessionScope.loggedUser.name}</p>
        <p class="fw-bold fs-4 mb-0"><fmt:message key="surname"/></p>
        <p>${sessionScope.loggedUser.surname}</p>
        <c:choose>
            <c:when test="${sessionScope.role eq 'BLOCKED'}">
                <p class="text-danger mt-2"><fmt:message key="blocked.account"/></p>
            </c:when>
            <c:otherwise>
                <a href="editProfile.jsp" class="btn btn-primary mt-2"><fmt:message key="edit.profile"/></a><br>
                <a href="controller?action=view-results" class="btn btn-primary mt-2"><fmt:message key="view.results"/></a><br>
            </c:otherwise>
        </c:choose>
        <a href="controller?action=sign-out" class="btn btn-danger mt-2 mb-3" role="button"><fmt:message key="sign.out"/></a>
    </div>

</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
