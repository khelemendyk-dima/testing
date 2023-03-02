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

    <div>
        <c:choose>
            <c:when test="${sessionScope.role eq 'ADMIN'}">
                <div class="row">
                    <div class="col-11">
                        <h3 class="mb-4">${requestScope.test.name}</h3>
                    </div>
                    <div class="col-1 d-flex justify-content-center align-items-center">
                        <a href="controller?action=test-pdf&testId=${requestScope.test.id}"><img src="img/PDF_icon.png" alt="pdf_icon" width="37" height="45"></a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <h3 class="mb-4">${requestScope.test.name}</h3>
            </c:otherwise>
        </c:choose>


        <div class="row">

            <div class="col">
                <p><fmt:message key="subject"/>: <fmt:message key="${requestScope.test.subject}"/></p>
                <p><fmt:message key="difficulty"/>: <fmt:message key="${requestScope.test.difficulty}"/></p>
            </div>
            <div class="col">
                <p><fmt:message key="test.duration"/>: ${requestScope.test.duration} <fmt:message key="minutes"/></p>
                <p><fmt:message key="number.of.queries"/>: ${requestScope.test.numberOfQueries}</p>
            </div>
            <div class="col text-end">
                <c:choose>
                    <c:when test="${sessionScope.role eq 'ADMIN'}">
                        <div>
                            <form action="controller" method="POST">
                                <input type="hidden" name="action" value="start-test">
                                <input type="hidden" name="id" value="${requestScope.test.id}">
                                <input type="hidden" name="duration" value="${requestScope.test.duration}">

                                <button class="btn btn-primary"><fmt:message key="solve"/></button>
                            </form>
                        </div>
                        <div>
                            <form action="editTest.jsp" method="GET">
                                <input type="hidden" name="id" value="${requestScope.test.id}">
                                <input type="hidden" name="name" value="${requestScope.test.name}">
                                <input type="hidden" name="subject" value="${requestScope.test.subject}">
                                <input type="hidden" name="difficulty" value="${requestScope.test.difficulty}">
                                <input type="hidden" name="duration" value="${requestScope.test.duration}">

                                <button type="submit" class="btn btn-primary mt-2"><fmt:message key="edit"/></button>
                            </form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <form action="controller" method="POST">
                                <input type="hidden" name="action" value="start-test">
                                <input type="hidden" name="id" value="${requestScope.test.id}">
                                <input type="hidden" name="duration" value="${requestScope.test.duration}">

                                <button class="btn btn-lg btn-primary mt-2"><fmt:message key="solve"/></button>
                            </form>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <c:if test="${sessionScope.role eq 'ADMIN'}">
            <div class="row mt-2 mb-5">
                <div class="col">
                    <form action="createQuestion.jsp" method="POST">
                        <input type="hidden" name="testId" value="${requestScope.test.id}">

                        <button type="submit" class="btn btn-primary text-start"><fmt:message key="add.question"/></button>
                    </form>
                </div>
                <div class="col text-end">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#deleteTestModal">
                        <fmt:message key="delete"/>
                    </button>
                </div>
            </div>
        </c:if>

        <!-- Modal delete test -->
        <div class="modal fade" id="deleteTestModal" tabindex="-1" aria-labelledby="deleteTestModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="deleteTestModalLabel"><fmt:message key="confirmation"/></h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <fmt:message key="delete.test"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="close"/></button>

                        <form action="controller" method="POST">
                            <input type="hidden" name="action" value="delete-test">
                            <input type="hidden" name="testId" value="${requestScope.test.id}">

                            <button type="submit" class="btn btn-danger"><fmt:message key="delete"/></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${sessionScope.role eq 'ADMIN'}">
        <c:set var="count" value="0" scope="page" />

        <c:forEach var="question" items="${requestScope.questions}">
            <c:set var="count" value="${count + 1}" scope="page"/>
            <div class="d-flex bg-secondary-subtle rounded mb-3 p-2 ">
                <div class="${sessionScope.role eq 'ADMIN' ? 'col-10' : 'col-12'}">
                    <div class="text-start fw-bold mb-3">${count}. ${question.text}</div>

                    <div class="row ps-4">
                        <c:forEach var="answer" items="${requestScope.answers}">
                            <c:if test="${answer.questionId eq question.id}">
                                <div class="col-6 mb-3 d-flex">
                                    <div class="rounded-1 ${answer.isCorrect() ? 'bg-success' : 'bg-dark-subtle'} me-2"
                                         style="min-width: 17px; height: 17px; margin-top: 5px;"></div>
                                    <div>${answer.text}</div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>

                <div class="col-2 text-end">
                    <form action="controller" method="GET">
                        <input type="hidden" name="action" value="search-question">
                        <input type="hidden" name="questionId" value="${question.id}">


                        <button type="submit" class="btn btn-primary mt-3"><fmt:message key="edit"/></button>
                    </form>

                    <form action="controller" method="POST">
                        <input type="hidden" name="action" value="delete-question">
                        <input type="hidden" name="questionId" value="${question.id}">
                        <input type="hidden" name="testId" value="${requestScope.test.id}">

                        <button type="submit" class="btn btn-danger mt-2">
                            <fmt:message key="delete"/>
                        </button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </c:if>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
