<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="editProfile"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
</head>

<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="text-center mx-auto" style="max-width: 330px;">
        <form action="controller" method="POST">
            <input type="hidden" name="action" value="edit-profile">
            <c:set var="error" value="${requestScope.error}"/>
            <c:set var="emailValue" value="${requestScope.user.email eq null ?
                                sessionScope.loggedUser.email : requestScope.user.email}"/>
            <c:set var="nameValue" value="${requestScope.user.name eq null ?
                                sessionScope.loggedUser.name : requestScope.user.name}"/>
            <c:set var="surnameValue" value="${requestScope.user.surname eq null ?
                                sessionScope.loggedUser.surname : requestScope.user.surname}"/>


            <h3 class="mb-4"><fmt:message key="editProfile"/></h3>

            <c:if test="${not empty requestScope.message}">
                <span class="text-success"><fmt:message key="${requestScope.message}"/></span>
            </c:if><br>
            <div class="form-floating">
                <input class="form-control" type="email" name="email" id="email"
                       placeholder="name@example.com" pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$"
                       value="${emailValue}" required>
                <label for="email"><fmt:message key="email"/></label>
                <c:if test="${fn:contains(error, 'email')}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>
            </div>

            <div class="form-floating">
                <input class="form-control" type="text" name="name" id="name"
                       placeholder="Name" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                       title="<fmt:message key="name.requirements"/>"
                       value="${nameValue}" required>
                <label for="name"><fmt:message key="name"/></label>
                <c:if test="${fn:contains(error, 'name')}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>
            </div>

            <div class="form-floating">
                <input class="form-control" type="text" name="surname" id="surname"
                       placeholder="Surname" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                       title="<fmt:message key="surname.requirements"/>"
                       value="${surnameValue}" required>
                <label for="surname"><fmt:message key="surname"/></label>
                <c:if test="${fn:contains(error, 'surname')}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>
            </div>

            <button class="w-100 btn btn-lg btn-primary mb-4 mt-4" type="submit"><fmt:message key="submit"/></button>
        </form>
        <p class="m-0">
            <a href="changePassword.jsp" class="link-dark"><fmt:message key="changePass"/></a>
        </p>
    </div>

    <jsp:include page="footer.jsp"/>

</div>

</body>
</html>
