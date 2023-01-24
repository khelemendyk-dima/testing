<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="user.info"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
</head>

<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="mx-auto" style="max-width: 400px;">

        <h3 class="text-center mb-4"><fmt:message key="user.info"/></h3>

        <c:set var="user" value="${requestScope.user}"/>

        <div class="fs-5">
            <p class="fw-bold mb-0"><fmt:message key="email"/>: </p>
            <span>${requestScope.user.email}</span>
            <p class="fw-bold mb-0 mt-1"><fmt:message key="name"/>: </p>
            <span>${requestScope.user.name}</span>
            <p class="fw-bold mb-0 mt-1"><fmt:message key="surname"/>: </p>
            <span>${requestScope.user.surname}</span>
            <p class="fw-bold mb-0 mt-1"><fmt:message key="role"/>: </p>
            <span><fmt:message key="${requestScope.user.role}"/></span>
        </div>

        <form class="mt-4 d-flex align-items-center" action="controller" method="POST">
            <input type="hidden" name="action" value="set-role">
            <input type="hidden" name="email" value=${requestScope.user.email}>

            <label>
                <select name="role" class="form-select">
                    <option value="ADMIN" ${requestScope.user.role eq 'ADMIN' ? 'selected' : ''}>
                        <fmt:message key="ADMIN"/>
                    </option>
                    <option value="STUDENT" ${requestScope.user.role eq 'STUDENT' ? 'selected' : ''}>
                        <fmt:message key="STUDENT"/>
                    </option>
                    <option value="BLOCKED" ${requestScope.user.role eq 'BLOCKED' ? 'selected' : ''}>
                        <fmt:message key="BLOCKED"/>
                    </option>
                </select>
            </label>
            <button type="submit" class="btn btn-primary ms-2"><fmt:message key="set.role"/></button>
        </form>

        <form action="editUserByAdmin.jsp" method="POST">
            <input type="hidden" name="id" value="${requestScope.user.id}">
            <input type="hidden" name="email" value="${requestScope.user.email}">
            <input type="hidden" name="name" value="${requestScope.user.name}">
            <input type="hidden" name="surname" value="${requestScope.user.surname}">

            <button type="submit" class="btn btn-primary mt-2"><fmt:message key="edit.profile"/></button>
        </form>

    </div>

    <jsp:include page="footer.jsp"/>

</div>

</body>
</html>
