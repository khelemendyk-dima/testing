<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. <fmt:message key="edit.question"/></title>
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
    <div class="mx-auto" style="max-width: 500px;">
        <form action="controller" method="POST">
            <input type="hidden" name="action" value="edit-question">
            <input type="hidden" name="questionId" value="${requestScope.question.id}">
            <input type="hidden" name="testId" value="${requestScope.question.testId}">

            <h3 class="text-center mb-4"><fmt:message key="edit.question"/></h3>

            <label class="ms-2 mb-2" for="questionText"><fmt:message key="your.question"/></label>
            <textarea class="form-control" name="questionText" id="questionText" maxlength="400"
                      required>${requestScope.question.text}</textarea>

            <div class="mt-4 bg-secondary-subtle rounded" id="answersDiv">
                <input id="numberOfAnswers" type="hidden" value="${requestScope.numberOfAnswers}">

                <c:if test="${not empty requestScope.error}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>

                <c:set var="count" value="0" scope="page" />

                <c:forEach var="answer" items="${requestScope.answers}">
                    <c:set var="count" value="${count + 1}" scope="page"/>

                    <div class="d-flex p-2" id="formCheckDiv${count}">
                        <div class="form-check d-flex align-items-center">
                            <input class="form-check-input" type="checkbox" name="correct${count}" value="true" id="correct${count}"
                                   ${answer.isCorrect() ? 'checked' : ''}>
                            <label for="correct${count}"></label>
                        </div>

                        <div class="form-floating ms-1 col">
                            <input class="form-control" type="text" name="answer${count}" id="answer${count}"
                                   placeholder="Answer" maxlength="150" value="${answer.text}" required>
                            <label for="answer${count}"><fmt:message key="answer"/> ${count}</label>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <span class="amountOfQuestionSpan">
                <button class="btn btn-primary mt-2" type="button" id="addTest">+</button>
                <button class="btn btn-danger mt-2" type="button" id="deleteTest">-</button>
            </span>

            <button class="w-100 btn btn-lg btn-primary mb-4 mt-4" type="submit"><fmt:message key="submit"/></button>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script src="js/add-questions.js"></script>

</body>
</html>
