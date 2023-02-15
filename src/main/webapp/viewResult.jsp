<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>TestHub. <fmt:message key="test.result"/></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="img/favicon.ico">
    <script src="js/bootstrap.bundle.min.js"></script>
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="container justify-content-center" style="max-width: 900px">
    <h3 class="mb-4">${requestScope.test.name}</h3>

    <div class="row">
        <div class="col">
            <p><fmt:message key="number.of.correct.answers"/>: ${requestScope.numberOfCorrectAnswers}/${requestScope.test.numberOfQueries}</p>
            <p><fmt:message key="your.score"/>: <fmt:formatNumber type="number" maxFractionDigits="2" value="${requestScope.score}"/>%</p>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
