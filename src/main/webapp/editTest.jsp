<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <title>TestHub. <fmt:message key="edit.test"/></title>
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
    <div class="text-center mx-auto" style="max-width: 500px;">
        <form action="controller" method="POST">
            <input type="hidden" name="action" value="edit-test">
            <input type="hidden" name="id" value="${requestScope.test.id eq null ?
                                param.id : requestScope.user.id}">

            <c:set var="nameValue" value="${requestScope.test.name eq null ?
                                param.name : requestScope.test.name}"/>
            <c:set var="subjectValue" value="${requestScope.test.subject eq null ?
                                param.subject : requestScope.test.subject}"/>
            <c:set var="difficultyValue" value="${requestScope.test.difficulty eq null ?
                                param.difficulty : requestScope.test.difficulty}"/>
            <c:set var="durationValue" value="${requestScope.test.duration eq null ?
                                param.duration : requestScope.test.duration}"/>

            <h3 class="mb-4"><fmt:message key="edit.test"/></h3>

            <c:if test="${not empty requestScope.error}">
                <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
            </c:if>

            <div class="form-floating">
                <input class="form-control" type="text" name="name" id="name"
                       placeholder="Name" pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'&quot:.,!?\- ]{1,100}"
                       value="${nameValue}"
                       title="<fmt:message key="test.name.requirements"/>"
                       required>
                <label for="name"><fmt:message key="test.name"/></label>
                <c:if test="${not empty requestScope.error}">
                    <span class="text-danger"><fmt:message key="${requestScope.error}"/></span>
                </c:if>
            </div>

            <div class="d-flex mt-4 justify-content-between align-items-center">
                <label>
                    <select name="subject" class="form-select py-3" required>
                        <option value=""><fmt:message key="select.subject"/></option>
                        <option value="ENGLISH" ${subjectValue eq 'English' ? 'selected' : ''}>
                            <fmt:message key="ENGLISH"/>
                        </option>
                        <option value="UKRAINIAN" ${subjectValue eq 'Ukrainian' ? 'selected' : ''}>
                            <fmt:message key="UKRAINIAN"/>
                        </option>
                        <option value="MATH" ${subjectValue eq 'Math' ? 'selected' : ''}>
                            <fmt:message key="MATH"/>
                        </option>
                        <option value="HISTORY" ${subjectValue eq 'History' ? 'selected' : ''}>
                            <fmt:message key="HISTORY"/>
                        </option>
                        <option value="BIOLOGY" ${subjectValue eq 'Biology' ? 'selected' : ''}>
                            <fmt:message key="BIOLOGY"/>
                        </option>
                        <option value="CHEMISTRY" ${subjectValue eq 'Chemistry' ? 'selected' : ''}>
                            <fmt:message key="CHEMISTRY"/>
                        </option>
                        <option value="PHYSICS" ${subjectValue eq 'Physics' ? 'selected' : ''}>
                            <fmt:message key="PHYSICS"/>
                        </option>
                    </select>
                </label>

                <label>
                    <select name="difficulty" class="form-select py-3" required>
                        <option value=""><fmt:message key="select.difficulty"/></option>
                        <option value="HARD" ${difficultyValue eq 'Hard' ? 'selected' : ''}>
                            <fmt:message key="HARD"/>
                        </option>
                        <option value="MEDIUM" ${difficultyValue eq 'Medium' ? 'selected' : ''}>
                            <fmt:message key="MEDIUM"/>
                        </option>
                        <option value="EASY" ${difficultyValue eq 'Easy' ? 'selected' : ''}>
                            <fmt:message key="EASY"/>
                        </option>
                    </select>
                </label>

                <div class="form-floating w-25">
                    <input class="form-control" type="number" name="duration" id="duration" value="${durationValue}"
                           placeholder="0" min="0">
                    <label for="duration"><fmt:message key="test.duration"/></label>
                </div>
            </div>

            <button class="w-100 btn btn-lg btn-primary mb-4 mt-4" type="submit"><fmt:message key="submit"/></button>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
