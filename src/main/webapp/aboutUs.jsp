<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="about"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
</head>

<body>

<div class="container">

    <jsp:include page="header.jsp"/>

    <div class="text-center">
        <h2><fmt:message key="about"/></h2>
        <p class="m-auto" style="max-width: 800px;"><fmt:message key="about.text"/></p>
    </div>

    <div>
        <div class="row text-center mt-5">
            <div class="col-lg-4">
                <p class="bg-secondary rounded py-1 text-white mx-auto" style="width: 75px;">120,542</p>
                <h4><fmt:message key="about.registered.users"/></h4>
            </div>
            <div class="col-lg-4">
                <p class="bg-secondary rounded py-1 text-white mx-auto" style="width: 75px;">10,047</p>
                <h4><fmt:message key="about.created.tests"/></h4>
            </div>
            <div class="col-lg-4">
                <p class="bg-secondary rounded py-1 text-white mx-auto" style="width: 75px;">995,479</p>
                <h4><fmt:message key="about.solved.tests"/></h4>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp"/>

</div>


</body>
</html>
