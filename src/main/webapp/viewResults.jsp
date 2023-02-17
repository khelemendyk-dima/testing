<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="view.results"/></title>
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
    <div class="text-center mx-auto" style="max-width: 700px;">

        <h3 class="mb-4"><fmt:message key="view.results"/></h3>

        <c:choose>
            <c:when test="${not empty requestScope.testResults}">
                <div class="bd-example-snippet bd-code-snippet">
                    <div class="bd-example">
                        <table class="table table-striped" aria-label="report-table">
                            <thead>
                            <tr>
                                <th class="text-start" scope="col"><fmt:message key="test.name"/></th>
                                <th scope="col"><fmt:message key="result"/></th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="testResult" items="${requestScope.testResults}">
                                <tr>
                                    <td class="text-start">${testResult.testName}</td>
                                    <td><fmt:formatNumber value="${testResult.result}" maxFractionDigits="1"/>%</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <p class="fs-6"><fmt:message key="no.passed.any.test"/></p>
            </c:otherwise>
        </c:choose>

    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
