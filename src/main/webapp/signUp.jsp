<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="sign.up"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/showPass.js"></script>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">

    <div class="text-center mx-auto" style="max-width: 330px;">
        <form action="controller" method="POST">
            <input type="hidden" name="action" value="sign-up">
            <c:set var="error" value="${requestScope.error}"/>

            <h3 class="mb-4"><fmt:message key="sign.up.title"/></h3>

            <div class="form-floating">
                <input class="form-control" type="email" name="email" id="email"
                       placeholder="name@example.com" pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$"
                       value="${requestScope.user.email}" required>
                <label for="email"><fmt:message key="email"/></label>
                <tags:contains error="${error}" value="email"/>
            </div>

            <div class="form-floating mt-2">
                <input class="form-control" type="text" name="name" id="name"
                       placeholder="Name" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                       title="<fmt:message key="name.requirements"/>"
                       value="${requestScope.user.name}" required>
                <label for="name"><fmt:message key="name"/></label>
                <tags:contains error="${error}" value="name"/>
            </div>

            <div class="form-floating mt-2">
                <input class="form-control" type="text" name="surname" id="surname"
                       placeholder="Surname" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                       title="<fmt:message key="surname.requirements"/>"
                       value="${requestScope.user.surname}" required>
                <label for="surname"><fmt:message key="surname"/></label>
                <tags:contains error="${error}" value="surname"/>
            </div>

            <div class="form-floating mt-2">
                <input class="form-control" type="password" name="password" id="password"
                       placeholder="Password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                       title="<fmt:message key="password.requirements"/>" required>
                <label for="password"><fmt:message key="password"/></label>
                <tags:contains error="${error}" value="pass"/>
            </div>

            <div class="form-floating mt-2">
                <input class="form-control" type="password" name="confirm-password" id="confirm-password"
                       placeholder="Password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                       title="<fmt:message key="password.requirements"/>" required>
                <label for="confirm-password"><fmt:message key="confirm.pass"/></label>
            </div>

            <div class="checkbox text-start mt-1 mx-3 mb-1">
                <input class="form-check-input" type="checkbox" id="flexCheckDefault"
                       onclick="showPass('password'); showPass('confirm-password');">
                <label class="form-check-label" for="flexCheckDefault">
                    <fmt:message key="show.pass"/>
                </label>
            </div>

            <div class="g-recaptcha" data-sitekey="6Lf3IcAkAAAAAM0wQKit8-gYW0PkTywoLQ6cTfw_"></div>
            <tags:contains error="${requestScope.error}" value="captcha"/><br>

            <button class="w-100 btn btn-lg btn-primary mb-4 mt-4" type="submit"><fmt:message key="sign.up"/></button>
        </form>

        <p class="m-0 mb-3">
            <fmt:message key="have.account"/>
            <a href="signIn.jsp" class="link-dark"><fmt:message key="sign.in"/></a>
        </p>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>