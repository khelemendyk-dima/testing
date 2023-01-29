<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>TestHub. <fmt:message key="contacts"/></title>
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

    <div>
        <h1 class="display-5 fw-bold mt-5 mb-3"><fmt:message key="contacts.title"/></h1>
        <p class="mb-4" style="max-width: 500px;"><fmt:message key="contacts.text"/></p>
        <dl>
            <div class="mt-4">
                <dt><fmt:message key="contacts.enquiries"/></dt>
                <dd>info@gmail.com</dd>
            </div>
            <div class="mt-4">
                <dt><fmt:message key="contacts.academy"/></dt>
                <dd>academy@gmail.com</dd>
            </div>
            <div class="mt-4">
                <dt><fmt:message key="contacts.telephone"/></dt>
                <dd>0113 288 6000</dd>
            </div>
            <div class="mt-4">
                <dt><fmt:message key="contacts.address"/></dt>
                <dd>Wike Ridge Lane, Shadwell, Leeds, LS17 9JW</dd>
            </div>
        </dl>
    </div>

</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
