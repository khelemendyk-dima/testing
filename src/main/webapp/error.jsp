<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="error"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container">

    <h3><fmt:message key="error"/></h3>
    <span class="fw-bold">Request from </span>
    <span>${pageContext.errorData.requestURI} is failed</span>
    <br>
    <span class="fw-bold">Servlet name or type: </span>
    <span>${pageContext.errorData.servletName}</span>
    <br>
    <span class="fw-bold">Status code: </span>
    <span>${pageContext.errorData.statusCode}</span>
    <br>
    <span class="fw-bold">Exception: </span>
    <span>${pageContext.errorData.throwable}</span>
    <br>
    <a href="index.jsp" class="btn btn-primary mt-2" role="button"><fmt:message key="to.home"/></a>

</div>

</body>
</html>
