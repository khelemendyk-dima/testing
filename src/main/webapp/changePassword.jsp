<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="change.pass"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/showPass.js"></script>
</head>

<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="text-center mx-auto" style="max-width: 330px;">
        <form action="controller" method="POST">
            <input type="hidden" name="action" value="change-password">

            <h3 class="mb-4"><fmt:message key="change.pass"/></h3>

            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if><br>
            <div class="form-floating">
                <input class="form-control" type="password" name="old-password" id="old-password"
                       placeholder="Password" required>
                <label for="old-password"><fmt:message key="old.pass"/></label>
                <c:if test="${not empty requestScope.error}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>
            </div>

            <div class="form-floating">
                <input class="form-control" type="password" name="password" id="password"
                       placeholder="Password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                       title="<fmt:message key="password.requirements"/>" required>
                <label for="password"><fmt:message key="new.pass"/></label>
            </div>

            <div class="form-floating">
                <input class="form-control" type="password" name="confirm-password" id="confirm-password"
                       placeholder="Password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,20}$"
                       title="<fmt:message key="password.requirements"/>" required>
                <label for="confirm-password"><fmt:message key="confirm.pass"/></label>
            </div>

            <div class="checkbox text-start mt-1 mx-3 mb-1">
                <input class="form-check-input" type="checkbox" id="flexCheckDefault"
                       onclick="showPass('old-password'); showPass('password'); showPass('confirm-password');">
                <label class="form-check-label" for="flexCheckDefault">
                    <fmt:message key="show.pass"/>
                </label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mb-4 mt-4" type="submit"><fmt:message key="change.pass"/></button>
        </form>
    </div>

    <jsp:include page="footer.jsp"/>

</div>

</body>
</html>
