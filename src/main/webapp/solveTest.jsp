<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">

<head>
    <title>TestHub. ${requestScope.test.name}</title>
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

    <div class="d-flex justify-content-between mb-3">
        <div class="p-2">
            <h3 class="mb-4">${requestScope.test.name}</h3>
        </div>
        <div class="text-center fs-5 p-2" style="min-width: 120px;">
            <p class="fw-bold"><fmt:message key="time.left"/></p>
            <p id="timer"></p>
        </div>
    </div>

    <form action="controller" id="id" method="POST">
        <input type="hidden" name="action" value="end-test">
        <input type="hidden" name="testId" id="testId" value="${requestScope.test.id}">
        <input type="hidden" name="testDuration" id="testDuration" value="${requestScope.test.duration}">
        <input type="hidden" name="finishTime" id="finishTime" value="${sessionScope.finishTime}">

        <c:set var="questionCount" value="0" scope="page" />
        <c:set var="answerCount" value="0" scope="page" />

        <c:forEach var="question" items="${requestScope.questions}">
            <c:set var="questionCount" value="${questionCount + 1}" scope="page"/>
            <div class="d-flex bg-secondary-subtle rounded mb-3 p-2">
                <div class="col-12">
                    <div class="text-start fw-bold mb-3">${questionCount}. ${question.text}</div>

                    <div class="row ps-4">
                        <c:forEach var="answer" items="${requestScope.answers}">
                            <c:if test="${answer.questionId eq question.id}">
                                <c:set var="answerCount" value="${answerCount + 1}" scope="page"/>
                                <div class="col-6 mb-3 d-flex">
                                    <input class="form-check-input me-2" type="checkbox" name="correct${answerCount}"
                                           value="true" id="correct${answerCount}">
                                    <label for="correct${answerCount}"></label>
                                    <div>${answer.text}</div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>

        <!-- Button trigger modal -->
        <button type="button" class="w-100 btn btn-lg btn-primary mb-4 mt-4" data-bs-toggle="modal" data-bs-target="#endTestModal">
            <fmt:message key="submit"/>
        </button>

        <!-- Modal end test -->
        <div class="modal fade" id="endTestModal" tabindex="-1" aria-labelledby="endTestModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="endTestModalLabel"><fmt:message key="confirmation"/></h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <fmt:message key="confirm.end.test"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="close"/></button>
                        <button type="submit" class="btn btn-danger"><fmt:message key="end.test"/></button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

<jsp:include page="footer.jsp"/>

<script src="js/timer.js"></script>

</body>
</html>
