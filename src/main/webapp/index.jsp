<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="home"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
</head>

<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="row align-items-center g-5 text-center">
        <div class="col-lg-6">
            <h1 class="display-5 fw-bold lh-1 mb-3"><fmt:message key="home.title"/></h1>
            <p class="lead"><fmt:message key="home.text"/></p>
        </div>
        <div class="col-lg-6">
            <img src="img/01.png" class="mx-lg-auto img-fluid" alt="testing image" width="510" height="495">
        </div>
    </div>

    <jsp:include page="footer.jsp"/>

</div>

</body>
</html>