<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="reset.password"/></title>
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
        <form method="POST" action="controller">
            <input type="hidden" name="action" value="reset-password">

            <h3 class="mb-4"><fmt:message key="reset.password"/></h3>

            <tags:notEmptyMessage value="${requestScope.message}"/>

            <div class="form-floating">
                <input class="form-control" type="email" name="email" id="email" value="${requestScope.email}"
                       placeholder="name@example.com" pattern="^[\w.%+-]+@[\w.-]+\.[a-zA-Z]{2,6}$"  required>
                <label for="email"><fmt:message key="email"/></label>

                <tags:notEmptyError value="${requestScope.error}"/>
            </div>

            <button type="submit" class="w-100 btn btn-lg btn-primary mb-4 mt-4"><fmt:message key="reset.password"/></button>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
