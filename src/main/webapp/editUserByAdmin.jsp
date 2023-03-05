<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="edit.profile"/></title>
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

    <div class="text-center mx-auto" style="max-width: 330px;">
        <form action="controller" method="POST">
            <input type="hidden" name="action" value="edit-user-by-admin">
            <input type="hidden" name="id" value="${requestScope.user.id eq null ?
                                param.id : requestScope.user.id}">

            <c:set var="error" value="${requestScope.error}"/>

            <c:set var="emailValue" value="${requestScope.user.email eq null ?
                                param.email : requestScope.user.email}"/>
            <c:set var="nameValue" value="${requestScope.user.name eq null ?
                                param.name : requestScope.user.name}"/>
            <c:set var="surnameValue" value="${requestScope.user.surname eq null ?
                                param.surname : requestScope.user.surname}"/>

            <h3 class="mb-4"><fmt:message key="edit.profile"/></h3>

            <tags:notEmptyMessage value="${requestScope.message}"/><br>

            <div class="form-floating">
                <input class="form-control" type="email" name="email" id="email"
                       placeholder="name@example.com" pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$"
                       value="${emailValue}" required>
                <label for="email"><fmt:message key="email"/></label>
                <tags:contains error="${error}" value="email"/>
            </div>

            <div class="form-floating">
                <input class="form-control" type="text" name="name" id="name"
                       placeholder="Name" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                       title="<fmt:message key="name.requirements"/>"
                       value="${nameValue}" required>
                <label for="name"><fmt:message key="name"/></label>
                <tags:contains error="${error}" value="name"/>
            </div>

            <div class="form-floating">
                <input class="form-control" type="text" name="surname" id="surname"
                       placeholder="Surname" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                       title="<fmt:message key="surname.requirements"/>"
                       value="${surnameValue}" required>
                <label for="surname"><fmt:message key="surname"/></label>
                <tags:contains error="${error}" value="surname"/>
            </div>

            <button class="w-100 btn btn-lg btn-primary mb-4 mt-4" type="submit"><fmt:message key="submit"/></button>
        </form>

    </div>

</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
