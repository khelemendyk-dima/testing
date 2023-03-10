<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="sign.in"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/showPass.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">

    <div class="text-center mx-auto" style="max-width: 330px;">
        <form action="controller" method="POST">
            <input type="hidden" name="action" value="sign-in">
            <c:set var="error" value="${requestScope.error}"/>

            <h3 class="mb-4"><fmt:message key="sign.in.title"/></h3>

            <tags:notEmptyMessage value="${requestScope.message}"/>
            <tags:contains error="${error}" value="access.denied"/>

            <div class="form-floating">
                <input class="form-control" type="email" name="email" id="email"
                       placeholder="name@example.com" pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$"
                       value="${requestScope.email}" required>
                <label for="email"><fmt:message key="email"/></label>
                <tags:contains error="${error}" value="email"/>
            </div>

            <div class="form-floating">
                <input class="form-control" type="password" name="password" id="password"
                       placeholder="Password" required>
                <label for="password"><fmt:message key="password"/></label>
                <tags:contains error="${error}" value="password"/>
            </div>

            <div class="checkbox text-start mx-3 my-1">
                <input class="form-check-input" type="checkbox" onclick="showPass('password')" id="flexCheckDefault">
                <label class="form-check-label" for="flexCheckDefault"><fmt:message key="show.pass"/></label>
            </div>

            <button class="w-100 btn btn-lg btn-primary my-4" type="submit"><fmt:message key="sign.in"/></button>

            <p class="mt-1">
                <fmt:message key="forgot.password"/>
                <a href="resetPassword.jsp" class="link-dark"><fmt:message key="reset.password"/></a>
            </p>

            <p class="mt-1">
                <fmt:message key="no.account"/>
                <a href="signUp.jsp" class="link-dark"><fmt:message key="sign.up"/></a>
            </p>
        </form>
    </div>

</div>

<jsp:include page="footer.jsp"/>

</body>
</html>